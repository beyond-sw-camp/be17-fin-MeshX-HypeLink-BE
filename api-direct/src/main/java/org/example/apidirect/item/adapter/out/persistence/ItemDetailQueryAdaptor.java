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
    public Page<StoreItemDetail> findByKeyword(String keyword, Pageable pageable) {
        return repository.findByKeyword(keyword, pageable)
                .map(ItemDetailMapper::toDomain);
    }

    @Override
    public Page<StoreItemDetail> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(ItemDetailMapper::toDomain);
    }

    @Override
    public Page<StoreItemDetail> findByCategory(String category, Pageable pageable) {
        return repository.findByCategory(category, pageable)
                .map(ItemDetailMapper::toDomain);
    }

    @Override
    public Page<StoreItemDetail> findByLowStock(Integer minStock, Pageable pageable) {
        return repository.findByLowStock(minStock, pageable)
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

    @Override
    public Optional<StoreItemDetail> findByIdWithLock(Integer id) {
        return repository.findByIdWithLock(id)
                .map(ItemDetailMapper::toDomain);
    }
}
