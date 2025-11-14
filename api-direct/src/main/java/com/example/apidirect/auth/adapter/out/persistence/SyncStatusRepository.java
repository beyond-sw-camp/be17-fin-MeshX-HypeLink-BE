package com.example.apidirect.auth.adapter.out.persistence;

import com.example.apidirect.auth.adapter.out.entity.SyncStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SyncStatusRepository extends JpaRepository<SyncStatusEntity, Integer> {
    Optional<SyncStatusEntity> findBySyncType(String syncType);
}
