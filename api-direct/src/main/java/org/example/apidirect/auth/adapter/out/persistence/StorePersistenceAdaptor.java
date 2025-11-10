package org.example.apidirect.auth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.auth.adapter.out.mapper.StoreMapper;
import org.example.apidirect.auth.domain.Store;
import org.example.apidirect.auth.usecase.port.out.StorePersistencePort;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class StorePersistenceAdaptor implements StorePersistencePort {

    private final StoreRepository storeRepository;

    @Override
    public Store save(Store store) {
        var entity = StoreMapper.toEntity(store);
        var saved = storeRepository.save(entity);
        return StoreMapper.toDomain(saved);
    }

    @Override
    public Optional<Store> findById(Integer id) {
        return storeRepository.findById(id)
                .map(StoreMapper::toDomain);
    }

    @Override
    public Optional<Store> findByStoreCode(String storeCode) {
        return storeRepository.findByStoreCode(storeCode)
                .map(StoreMapper::toDomain);
    }
}
