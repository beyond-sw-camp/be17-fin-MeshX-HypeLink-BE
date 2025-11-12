package org.example.apidirect.item.usecase.port.in;

import org.example.apidirect.item.adapter.in.web.dto.response.StoreItemDetailResponse;
import org.example.apidirect.item.domain.StoreItemDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemQueryPort {
    Page<StoreItemDetailResponse> findAllItems(Pageable pageable);

    Page<StoreItemDetailResponse> searchItems(String keyword, Pageable pageable);

    Page<StoreItemDetailResponse> findItemsByCategory(String category, Pageable pageable);

    Page<StoreItemDetailResponse> findLowStockItems(Integer minStock, Pageable pageable);

    StoreItemDetailResponse findItemByBarcode(String itemDetailCode);

    // Payment에서 재고 조회용 (비관적 락)
    StoreItemDetail findByIdWithLock(Integer itemDetailId);
}
