package com.example.apidirect.auth.usecase.port.out;

import com.example.apidirect.auth.domain.Store;

import java.util.Optional;

public interface StorePersistencePort {
    Store save(Store store);
    Optional<Store> findById(Integer id);
    Optional<Store> findByStoreCode(String storeCode);
}
