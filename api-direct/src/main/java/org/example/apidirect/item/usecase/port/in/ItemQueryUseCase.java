package org.example.apidirect.item.usecase.port.in;

import org.example.apidirect.item.adapter.in.web.dto.response.StoreItemDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemQueryUseCase {
    Page<StoreItemDetailResponse> findAllItems(Pageable pageable);

    Page<StoreItemDetailResponse> searchItems(String keyword, Pageable pageable);

    Page<StoreItemDetailResponse> findItemsByCategory(String category, Pageable pageable);

    Page<StoreItemDetailResponse> findLowStockItems(Integer minStock, Pageable pageable);

    StoreItemDetailResponse findItemByBarcode(String itemDetailCode);
}
