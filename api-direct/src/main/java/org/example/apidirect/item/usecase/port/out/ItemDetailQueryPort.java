package org.example.apidirect.item.usecase.port.out;

import org.example.apidirect.item.domain.StoreItemDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ItemDetailQueryPort {
    Page<StoreItemDetail> findByStoreId(Integer storeId, Pageable pageable);
    Page<StoreItemDetail> findByStoreIdAndKeyword(Integer storeId, String keyword, Pageable pageable);
    Page<StoreItemDetail> findByStoreIdAndCategory(Integer storeId, String category, Pageable pageable);
    Page<StoreItemDetail> findByStoreIdAndLowStock(Integer storeId, Integer minStock, Pageable pageable);
    Optional<StoreItemDetail> findByItemDetailCode(String itemDetailCode);
    Optional<StoreItemDetail> findByItemDetailCodeWithLock(String itemDetailCode);
}
