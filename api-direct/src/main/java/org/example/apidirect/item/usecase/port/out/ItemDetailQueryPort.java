package org.example.apidirect.item.usecase.port.out;

import org.example.apidirect.item.domain.StoreItemDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ItemDetailQueryPort {
    Page<StoreItemDetail> findByKeyword(String keyword, Pageable pageable);
    Page<StoreItemDetail> findAll(Pageable pageable);
    Page<StoreItemDetail> findByCategory(String category, Pageable pageable);
    Page<StoreItemDetail> findByLowStock(Integer minStock, Pageable pageable);
    Optional<StoreItemDetail> findByItemDetailCode(String itemDetailCode);
    Optional<StoreItemDetail> findByItemDetailCodeWithLock(String itemDetailCode);
    Optional<StoreItemDetail> findByIdWithLock(Integer id);
}
