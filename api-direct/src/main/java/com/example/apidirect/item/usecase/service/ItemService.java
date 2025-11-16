package com.example.apidirect.item.usecase.service;

import MeshX.common.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.apidirect.auth.domain.POS;
import com.example.apidirect.auth.domain.Store;
import com.example.apidirect.auth.usecase.port.in.POSQueryPort;
import com.example.apidirect.auth.usecase.port.in.StoreQueryPort;
import com.example.apidirect.common.exception.ItemException;
import com.example.apidirect.common.exception.ItemExceptionMessage;
import com.example.apidirect.item.adapter.in.web.dto.request.*;
import com.example.apidirect.item.adapter.in.web.dto.response.StoreItemDetailResponse;
import com.example.apidirect.item.adapter.in.web.mapper.StoreItemDetailResponseMapper;
import com.example.apidirect.item.adapter.out.entity.StoreItemDetailEntity;
import com.example.apidirect.item.adapter.out.entity.StoreItemEntity;
import com.example.apidirect.item.adapter.out.mapper.ItemDetailMapper;
import com.example.apidirect.item.adapter.out.persistence.StoreItemDetailBatchRepository;
import com.example.apidirect.item.adapter.out.persistence.StoreItemRepository;
import com.example.apidirect.item.domain.Category;
import com.example.apidirect.item.domain.StoreItem;
import com.example.apidirect.item.domain.StoreItemDetail;
import com.example.apidirect.item.domain.StoreItemImage;
import com.example.apidirect.item.usecase.port.in.ItemCommandPort;
import com.example.apidirect.item.usecase.port.in.ItemQueryPort;
import com.example.apidirect.item.usecase.port.out.CategoryPersistencePort;
import com.example.apidirect.item.usecase.port.out.ItemDetailPersistencePort;
import com.example.apidirect.item.usecase.port.out.ItemDetailQueryPort;
import com.example.apidirect.item.usecase.port.out.ItemPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService implements ItemQueryPort, ItemCommandPort {

    private final ItemDetailQueryPort itemDetailQueryPort;
    private final ItemDetailPersistencePort itemDetailPersistencePort;
    private final ItemPersistencePort itemPersistencePort;
    private final POSQueryPort posQueryPort;
    private final StoreQueryPort storeQueryPort;
    private final CategoryPersistencePort categoryPersistencePort;
    private final StoreItemDetailBatchRepository storeItemDetailBatchRepository;
    private final StoreItemRepository itemRepository;

    @Override
    public Page<StoreItemDetailResponse> findAllItems(Integer memberId, Pageable pageable) {
        Integer storeId = getStoreId(memberId);
        log.info("=== findAllItems - memberId: {}, storeId: {}", memberId, storeId);

        Page<StoreItemDetail> details = itemDetailQueryPort.findAll(storeId, pageable);
        log.info("=== findAllItems - totalElements: {}, size: {}",
                 details.getTotalElements(), details.getContent().size());

        if (!details.isEmpty()) {
            StoreItemDetail first = details.getContent().get(0);
            log.info("=== first item - id: {}, itemCode: {}, koName: {}, category: {}, amount: {}",
                     first.getId(), first.getItemCode(), first.getKoName(), first.getCategory(), first.getAmount());
        }

        return details.map(this::toResponse);
    }

    @Override
    public Page<StoreItemDetailResponse> searchItems(Integer memberId, String keyword, Pageable pageable) {
        Integer storeId = getStoreId(memberId);
        Page<StoreItemDetail> details;
        if (keyword == null || keyword.isBlank()){
            details = itemDetailQueryPort.findAll(storeId, pageable);
        }
        else{
            details  = itemDetailQueryPort.findByKeyword(storeId, keyword, pageable);
        }
        return details.map(this::toResponse);
    }

    @Override
    public Page<StoreItemDetailResponse> findItemsByCategory(Integer memberId, String category, Pageable pageable) {
        Integer storeId = getStoreId(memberId);
        Page<StoreItemDetail> details = itemDetailQueryPort.findByCategory(storeId, category, pageable);
        return details.map(this::toResponse);
    }

    @Override
    public Page<StoreItemDetailResponse> findLowStockItems(Integer memberId, Integer minStock, Pageable pageable) {
        Integer storeId = getStoreId(memberId);
        Page<StoreItemDetail> details = itemDetailQueryPort.findByLowStock(storeId, minStock, pageable);
        return details.map(this::toResponse);
    }

    private Integer getStoreId(Integer memberId) {
        POS pos = posQueryPort.findByMemberId(memberId);
        return pos.getStoreId();
    }

    @Override
    public StoreItemDetailResponse findItemByBarcode(String itemDetailCode) {
        StoreItemDetail detail = itemDetailQueryPort.findByItemDetailCode(itemDetailCode)
                .orElseThrow(() -> new ItemException(ItemExceptionMessage.NOT_FOUND));
        return toResponse(detail);
    }

    @Override
    public StoreItemDetailResponse findItemDetail(String itemCode, String itemDetailCode, Integer storeId) {
        StoreItemDetail detail = itemDetailQueryPort.findByItemCodeAndItemDetailCodeAndStoreId(itemCode, itemDetailCode, storeId)
                .orElseThrow(() -> new ItemException(ItemExceptionMessage.NOT_FOUND));
        return toResponse(detail);
    }

    @Override
    public StoreItemDetail findByIdWithLock(Integer itemDetailId) {
        return itemDetailQueryPort.findByIdWithLock(itemDetailId)
                .orElseThrow(() -> new ItemException(ItemExceptionMessage.NOT_FOUND));
    }

    @Override
    @Transactional
    public void updateStock(String itemDetailCode, Integer stockChange) {
        StoreItemDetail detail = itemDetailQueryPort.findByItemDetailCodeWithLock(itemDetailCode)
                .orElseThrow(() -> new ItemException(ItemExceptionMessage.NOT_FOUND));

        detail.updateStock(stockChange);
        itemDetailPersistencePort.save(detail);
    }

    @Override
    @Transactional
    public void saveAllItemsFromHeadOffice(SaveStoreItemListRequest request) {
        Integer storeId = request.getStoreId();
        log.info("=== saveAllItemsFromHeadOffice - storeId: {}, items: {}", storeId, request.getItems().size());

        List<StoreItemDetailEntity> allDetailEntities = new ArrayList<>();

        request.getItems().forEach(itemReq -> {
            // 1. StoreItem 생성 및 저장
            StoreItem storeItem = StoreItem.builder()
                    .itemCode(itemReq.getItemCode())
                    .storeId(storeId)
                    .unitPrice(itemReq.getUnitPrice())
                    .amount(itemReq.getAmount())
                    .enName(itemReq.getEnName())
                    .koName(itemReq.getKoName())
                    .content(itemReq.getContent())
                    .company(itemReq.getCompany())
                    .category(itemReq.getCategory())
                    .build();

            StoreItem savedItem = itemPersistencePort.save(storeItem);

            // 2. StoreItemDetail 엔티티 생성 (배치 저장을 위해 수집)
            if (itemReq.getItemDetails() != null && !itemReq.getItemDetails().isEmpty()) {
                // ✅ DB에서 실제 영속성 Entity 조회
                StoreItemEntity itemEntity = itemRepository.findByItemCodeAndStoreId(
                        savedItem.getItemCode(), savedItem.getStoreId())
                        .orElseThrow(() -> new RuntimeException("저장된 Item을 찾을 수 없습니다: " + savedItem.getItemCode()));

                List<StoreItemDetailEntity> detailEntities = itemReq.getItemDetails().stream()
                        .map(detailReq -> StoreItemDetailEntity.builder()
                                .itemDetailCode(detailReq.getItemDetailCode())
                                .color(detailReq.getColor())
                                .colorCode(detailReq.getColorCode())
                                .size(detailReq.getSize())
                                .stock(detailReq.getStock())
                                .item(itemEntity)  // ← 이제 영속성 Entity!
                                .build())
                        .toList();

                allDetailEntities.addAll(detailEntities);
            }

            // 3. StoreItemImage 저장 (나중에 필요시 구현)
            // itemReq.getImages()로 저장
        });

        log.info("=== Total details to save: {}", allDetailEntities.size());
        // 모든 StoreItemDetail을 배치로 저장 (중복 자동 스킵)
        if (!allDetailEntities.isEmpty()) {
            storeItemDetailBatchRepository.saveAllSkipDuplicate(allDetailEntities);
        }
        log.info("=== saveAllItemsFromHeadOffice completed");
    }

    @Override
    @Transactional
    public void saveAllCategoriesFromHeadOffice(SaveStoreCategoriesRequest request) {
        // Category 리스트를 받아서 저장 (중복은 자동으로 스킵)
        if (request.getCategories() != null && !request.getCategories().isEmpty()) {
            List<String> categoryNames = request.getCategories().stream()
                    .map(SaveStoreCategoryRequest::getCategory)
                    .toList();
            categoryPersistencePort.saveAll(categoryNames, request.getStoreId());
        }
    }

    private StoreItemDetailResponse toResponse(StoreItemDetail detail) {
        // StoreItem 정보는 이미 detail 도메인 객체에 포함되어 있음 (별도 조회 불필요)
        return StoreItemDetailResponseMapper.toResponse(
                detail,
                detail.getKoName(),
                detail.getCategory(),
                detail.getAmount()
        );
    }
}
