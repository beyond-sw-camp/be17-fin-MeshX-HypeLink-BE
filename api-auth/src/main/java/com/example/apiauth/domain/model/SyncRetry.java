package com.example.apiauth.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SyncRetry {

    private Integer id;
    private String entityType;
    private Integer entityId;
    private String eventJson;
    private String errorMessage;
    private LocalDateTime failedAt;
    private LocalDateTime retryAt;
    private Integer retryCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static SyncRetry create(String entityType, Integer entityId, String eventJson, String errorMessage, LocalDateTime retryAt) {
        LocalDateTime now = LocalDateTime.now();
        return SyncRetry.builder()
                .entityType(entityType)
                .entityId(entityId)
                .eventJson(eventJson)
                .errorMessage(errorMessage)
                .failedAt(now)
                .retryAt(retryAt)
                .retryCount(0)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public SyncRetry increaseRetryCount() {
        return SyncRetry.builder()
                .id(this.id)
                .entityType(this.entityType)
                .entityId(this.entityId)
                .eventJson(this.eventJson)
                .errorMessage(this.errorMessage)
                .failedAt(this.failedAt)
                .retryAt(LocalDateTime.now().plusMinutes(5))
                .retryCount(this.retryCount + 1)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
