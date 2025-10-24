package MeshX.HypeLink.direct_store.item.service;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.direct_store.item.model.dto.request.SaveStoreItemListReq;
import MeshX.HypeLink.direct_store.item.model.dto.response.StoreItemDetailListRes;
import MeshX.HypeLink.direct_store.item.model.dto.response.StoreItemDetailRes;
import MeshX.HypeLink.direct_store.item.model.entity.StoreCategory;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItemImage;
import MeshX.HypeLink.direct_store.item.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreItemService {
    private final StoreJpaRepositoryVerify storeRepository;
    private final StoreItemJpaRepositoryVerify storeItemRepository;
    private final StoreItemDetailRepositoryVerify storeItemDetailRepository;
    private final StoreItemDetailRepository storeItemDetailQueryRepository;  // 조회용
    private final StoreItemImageRepositoryVerify storeItemImageRepository;
    private final StoreCategoryJpaRepositoryVerify storeCategoryRepository;

    @Transactional
    public void saveAll(SaveStoreItemListReq dto) {
        Store store = storeRepository.findById(dto.getStoreId());

        dto.getItems().forEach(one -> {
            StoreCategory category = storeCategoryRepository.findByCategory(one.getCategory());

            StoreItem item = one.toEntity(store, category);
            StoreItem storeItem = storeItemRepository.save(item);

            List<StoreItemDetail> itemDetails = one.getItemDetails().stream().map(detail -> detail.toEntity(storeItem)).toList();
            storeItemDetailRepository.saveAllSkipDuplicate(itemDetails);

            List<StoreItemImage> images = one.getImages().stream().map(image -> image.toEntity(storeItem)).toList();
            storeItemImageRepository.saveAllSkipDuplicate(images);
        });
    }

    // 특정 매장의 전체 상품 조회 (페이징)
    public PageRes<StoreItemDetailRes> findItemDetailsByStoreId(Integer storeId, Pageable pageable) {
        Page<StoreItemDetail> page = storeItemDetailQueryRepository.findByStoreId(storeId, pageable);
        Page<StoreItemDetailRes> mapped = page.map(StoreItemDetailRes::toDto);
        return PageRes.toDto(mapped);
    }

    // 특정 매장의 상품 검색 (페이징) - 한글명, 영문명, 바코드 검색
    public PageRes<StoreItemDetailRes> findItemDetailsByStoreIdAndSearch(Integer storeId, String keyword, Pageable pageable) {
        Page<StoreItemDetail> page = storeItemDetailQueryRepository.findByStoreIdAndName(storeId, keyword, pageable);
        Page<StoreItemDetailRes> mapped = page.map(StoreItemDetailRes::toDto);
        return PageRes.toDto(mapped);
    }

    // 특정 매장의 카테고리별 조회 (페이징)
    public PageRes<StoreItemDetailRes> findItemDetailsByStoreIdAndCategory(Integer storeId, String category, Pageable pageable) {
        Page<StoreItemDetail> page = storeItemDetailQueryRepository.findByStoreIdAndCategory(storeId, category, pageable);
        Page<StoreItemDetailRes> mapped = page.map(StoreItemDetailRes::toDto);
        return PageRes.toDto(mapped);
    }

    // 특정 매장의 재고 부족 상품 조회 (페이징)
    public PageRes<StoreItemDetailRes> findItemDetailsByStoreIdAndLowStock(Integer storeId, Integer minStock, Pageable pageable) {
        Page<StoreItemDetail> page = storeItemDetailQueryRepository.findByStoreIdAndLowStock(storeId, minStock, pageable);
        Page<StoreItemDetailRes> mapped = page.map(StoreItemDetailRes::toDto);
        return PageRes.toDto(mapped);
    }

    // 바코드로 상품 조회
    public StoreItemDetailRes findItemDetailByBarcode(String itemDetailCode) {
        StoreItemDetail detail = storeItemDetailQueryRepository.findByItemDetailCode(itemDetailCode)
                .orElseThrow(() -> new RuntimeException("Item not found with barcode: " + itemDetailCode));
        return StoreItemDetailRes.toDto(detail);
    }
}
