package com.example.apiauth.adapter.out.persistence;

import com.example.apiauth.adapter.out.persistence.entity.SyncRetryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SyncRetryJpaRepository extends JpaRepository<SyncRetryEntity, Integer> {

    @Query("SELECT s FROM SyncRetryEntity s WHERE s.retryAt <= :now ORDER BY s.retryAt ASC")
    List<SyncRetryEntity> findAllRetryTargets(LocalDateTime now);
}
