package org.example.apidirect.item.usecase.service;

import MeshX.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.common.exception.ItemException;
import org.example.apidirect.common.exception.ItemExceptionMessage;
import org.example.apidirect.item.adapter.in.web.dto.response.StoreItemDetailResponse;
import org.example.apidirect.item.adapter.in.web.mapper.StoreItemDetailResponseMapper;
import org.example.apidirect.item.domain.StoreItem;
import org.example.apidirect.item.domain.StoreItemDetail;
import org.example.apidirect.item.usecase.port.in.ItemCommandPort;
import org.example.apidirect.item.usecase.port.in.ItemQueryPort;
import org.example.apidirect.item.usecase.port.out.ItemDetailPersistencePort;
import org.example.apidirect.item.usecase.port.out.ItemDetailQueryPort;
import org.example.apidirect.item.usecase.port.out.ItemPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService implements ItemQueryPort, ItemCommandPort {

    private final ItemDetailQueryPort itemDetailQueryPort;
    private final ItemDetailPersistencePort itemDetailPersistencePort;
    private final ItemPersistencePort itemPersistencePort;

    @Override
    public Page<StoreItemDetailResponse> findAllItems(Pageable pageable) {
        Page<StoreItemDetail> details = itemDetailQueryPort.findAll(pageable);
        return details.map(this::toResponse);
    }

    @Override
    public Page<StoreItemDetailResponse> searchItems(String keyword, Pageable pageable) {
        Page<StoreItemDetail> details;
        if (keyword == null || keyword.isBlank()){
            details = itemDetailQueryPort.findAll(pageable);
        }
        else{
            details  = itemDetailQueryPort.findByKeyword(keyword, pageable);
        }
        return details.map(this::toResponse);
    }

    @Override
    public Page<StoreItemDetailResponse> findItemsByCategory(String category, Pageable pageable) {
        Page<StoreItemDetail> details = itemDetailQueryPort.findByCategory(category, pageable);
        return details.map(this::toResponse);
    }

    @Override
    public Page<StoreItemDetailResponse> findLowStockItems(Integer minStock, Pageable pageable) {
        Page<StoreItemDetail> details = itemDetailQueryPort.findByLowStock(minStock, pageable);
        return details.map(this::toResponse);
    }

    @Override
    public StoreItemDetailResponse findItemByBarcode(String itemDetailCode) {
        StoreItemDetail detail = itemDetailQueryPort.findByItemDetailCode(itemDetailCode)
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

    private StoreItemDetailResponse toResponse(StoreItemDetail detail) {
        // StoreItem 정보 조회
        StoreItem item = itemPersistencePort.findByItemCode(detail.getItemCode())
                .orElseThrow(() -> new ItemException(ItemExceptionMessage.NOT_FOUND));

        return StoreItemDetailResponseMapper.toResponse(
                detail,
                item.getKoName(),
                item.getCategory(),
                item.getAmount()
        );
    }
}
