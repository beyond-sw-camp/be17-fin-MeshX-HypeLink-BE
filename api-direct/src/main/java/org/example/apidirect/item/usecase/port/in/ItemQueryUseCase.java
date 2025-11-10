package org.example.apidirect.item.usecase.port.in;

import org.example.apidirect.item.adapter.in.web.dto.response.StoreItemDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemQueryUseCase {
    Page<StoreItemDetailResponse> findItemsByStoreId(Integer storeId, Pageable pageable);
    Page<StoreItemDetailResponse> searchItems(Integer storeId, String keyword, Pageable pageable);
    Page<StoreItemDetailResponse> findItemsByCategory(Integer storeId, String category, Pageable pageable);
    Page<StoreItemDetailResponse> findLowStockItems(Integer storeId, Integer minStock, Pageable pageable);
    StoreItemDetailResponse findItemByBarcode(String itemDetailCode);
}
