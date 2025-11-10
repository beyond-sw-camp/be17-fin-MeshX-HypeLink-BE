package org.example.apidirect.auth.adapter.out.persistence;

import org.example.apidirect.auth.adapter.out.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, Integer> {
    Optional<StoreEntity> findByStoreCode(String storeCode);
}
