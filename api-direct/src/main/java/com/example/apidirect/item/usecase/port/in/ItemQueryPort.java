package com.example.apidirect.item.usecase.port.in;

import com.example.apidirect.item.adapter.in.web.dto.response.StoreItemDetailResponse;
import com.example.apidirect.item.domain.StoreItemDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemQueryPort {
    Page<StoreItemDetailResponse> findAllItems(Integer memberId, Pageable pageable);

    Page<StoreItemDetailResponse> searchItems(Integer memberId, String keyword, Pageable pageable);

    Page<StoreItemDetailResponse> findItemsByCategory(Integer memberId, String category, Pageable pageable);

    Page<StoreItemDetailResponse> findLowStockItems(Integer memberId, Integer minStock, Pageable pageable);

    StoreItemDetailResponse findItemByBarcode(String itemDetailCode);

    StoreItemDetailResponse findItemDetail(String itemCode, String itemDetailCode, Integer storeId);

    // Payment에서 재고 조회용 (비관적 락)
    StoreItemDetail findByIdWithLock(Integer itemDetailId);
}
