package com.example.apidirect.item.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import com.example.apidirect.item.adapter.out.mapper.ItemDetailMapper;
import com.example.apidirect.item.domain.StoreItemDetail;
import com.example.apidirect.item.usecase.port.out.ItemDetailQueryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class ItemDetailQueryAdaptor implements ItemDetailQueryPort {

    private final StoreItemDetailRepository repository;

    @Override
    public Page<StoreItemDetail> findByKeyword(Integer storeId, String keyword, Pageable pageable) {
        return repository.findByStoreIdAndKeyword(storeId, keyword, pageable)
                .map(ItemDetailMapper::toDomain);
    }

    @Override
    public Page<StoreItemDetail> findAll(Integer storeId, Pageable pageable) {
        return repository.findAllByStoreId(storeId, pageable)
                .map(ItemDetailMapper::toDomain);
    }

    @Override
    public Page<StoreItemDetail> findByCategory(Integer storeId, String category, Pageable pageable) {
        return repository.findByStoreIdAndCategory(storeId, category, pageable)
                .map(ItemDetailMapper::toDomain);
    }

    @Override
    public Page<StoreItemDetail> findByLowStock(Integer storeId, Integer minStock, Pageable pageable) {
        return repository.findByStoreIdAndLowStock(storeId, minStock, pageable)
                .map(ItemDetailMapper::toDomain);
    }

    @Override
    public Optional<StoreItemDetail> findByItemDetailCode(String itemDetailCode) {
        return repository.findByItemDetailCode(itemDetailCode)
                .map(ItemDetailMapper::toDomain);
    }

    @Override
    public Optional<StoreItemDetail> findByItemDetailCodeAndStoreId(String itemDetailCode, Integer storeId) {
        return repository.findByItemDetailCodeAndStoreId(itemDetailCode, storeId)
                .map(ItemDetailMapper::toDomain);
    }

    @Override
    public Optional<StoreItemDetail> findByItemCodeAndItemDetailCodeAndStoreId(String itemCode, String itemDetailCode, Integer storeId) {
        return repository.findByItemCodeAndItemDetailCodeAndStoreId(itemCode, itemDetailCode, storeId)
                .map(ItemDetailMapper::toDomain);
    }

    @Override
    public Optional<StoreItemDetail> findByItemDetailCodeWithLock(String itemDetailCode) {
        return repository.findByItemDetailCodeWithLock(itemDetailCode)
                .map(ItemDetailMapper::toDomain);
    }

    @Override
    public Optional<StoreItemDetail> findByIdWithLock(Integer id) {
        return repository.findByIdWithLock(id)
                .map(ItemDetailMapper::toDomain);
    }
}
