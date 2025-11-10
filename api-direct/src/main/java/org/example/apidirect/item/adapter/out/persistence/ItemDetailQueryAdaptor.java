package org.example.apidirect.item.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.item.adapter.out.mapper.ItemDetailMapper;
import org.example.apidirect.item.domain.StoreItemDetail;
import org.example.apidirect.item.usecase.port.out.ItemDetailQueryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class ItemDetailQueryAdaptor implements ItemDetailQueryPort {

    private final StoreItemDetailRepository repository;

    @Override
    public Page<StoreItemDetail> findByStoreId(Integer storeId, Pageable pageable) {
        return repository.findByStoreId(storeId, pageable)
                .map(ItemDetailMapper::toDomain);
    }

    @Override
    public Page<StoreItemDetail> findByStoreIdAndKeyword(Integer storeId, String keyword, Pageable pageable) {
        return repository.findByStoreIdAndKeyword(storeId, keyword, pageable)
                .map(ItemDetailMapper::toDomain);
    }

    @Override
    public Page<StoreItemDetail> findByStoreIdAndCategory(Integer storeId, String category, Pageable pageable) {
        return repository.findByStoreIdAndCategory(storeId, category, pageable)
                .map(ItemDetailMapper::toDomain);
    }

    @Override
    public Page<StoreItemDetail> findByStoreIdAndLowStock(Integer storeId, Integer minStock, Pageable pageable) {
        return repository.findByStoreIdAndLowStock(storeId, minStock, pageable)
                .map(ItemDetailMapper::toDomain);
    }

    @Override
    public Optional<StoreItemDetail> findByItemDetailCode(String itemDetailCode) {
        return repository.findByItemDetailCode(itemDetailCode)
                .map(ItemDetailMapper::toDomain);
    }

    @Override
    public Optional<StoreItemDetail> findByItemDetailCodeWithLock(String itemDetailCode) {
        return repository.findByItemDetailCodeWithLock(itemDetailCode)
                .map(ItemDetailMapper::toDomain);
    }
}
