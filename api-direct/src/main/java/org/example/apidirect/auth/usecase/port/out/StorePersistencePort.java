package org.example.apidirect.auth.usecase.port.out;

import org.example.apidirect.auth.domain.Store;

import java.util.Optional;

public interface StorePersistencePort {
    Store save(Store store);
    Optional<Store> findById(Integer id);
    Optional<Store> findByStoreCode(String storeCode);
}
