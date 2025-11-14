package com.example.apiauth.adapter.out.persistence.mapper;

import com.example.apiauth.adapter.out.persistence.entity.SyncRetryEntity;
import com.example.apiauth.domain.model.SyncRetry;

public class SyncRetryMapper {

    private SyncRetryMapper() {}

    public static SyncRetry toDomain(SyncRetryEntity entity) {
        return SyncRetry.builder()
                .id(entity.getId())
                .entityType(entity.getEntityType())
                .entityId(entity.getEntityId())
                .eventJson(entity.getEventJson())
                .errorMessage(entity.getErrorMessage())
                .failedAt(entity.getFailedAt())
                .retryAt(entity.getRetryAt())
                .retryCount(entity.getRetryCount())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static SyncRetryEntity toEntity(SyncRetry domain) {
        return SyncRetryEntity.builder()
                .id(domain.getId())
                .entityType(domain.getEntityType())
                .entityId(domain.getEntityId())
                .eventJson(domain.getEventJson())
                .errorMessage(domain.getErrorMessage())
                .failedAt(domain.getFailedAt())
                .retryAt(domain.getRetryAt())
                .retryCount(domain.getRetryCount())
                .build();
    }
}
