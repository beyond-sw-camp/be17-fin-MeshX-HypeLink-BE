package com.example.apidirect.item.usecase.port.out;

import com.example.apidirect.item.domain.StoreItemDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ItemDetailQueryPort {
    Page<StoreItemDetail> findByKeyword(Integer storeId, String keyword, Pageable pageable);
    Page<StoreItemDetail> findAll(Integer storeId, Pageable pageable);
    Page<StoreItemDetail> findByCategory(Integer storeId, String category, Pageable pageable);
    Page<StoreItemDetail> findByLowStock(Integer storeId, Integer minStock, Pageable pageable);
    Optional<StoreItemDetail> findByItemDetailCode(String itemDetailCode);
    Optional<StoreItemDetail> findByItemDetailCodeAndStoreId(String itemDetailCode, Integer storeId);
    Optional<StoreItemDetail> findByItemDetailCodeWithLock(String itemDetailCode);
    Optional<StoreItemDetail> findByIdWithLock(Integer id);
    Optional<StoreItemDetail> findByItemCodeAndItemDetailCodeAndStoreId(String itemCode, String itemDetailCode, Integer storeId);
}
