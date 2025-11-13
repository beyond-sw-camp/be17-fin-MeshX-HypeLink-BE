package com.example.apiauth.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataSyncEvent {

    private SyncOperation operation;
    private EntityType entityType;
    private Integer entityId;
    private String entityData; // JSON 형태의 엔티티 데이터
    private LocalDateTime timestamp;

    public enum SyncOperation {
        CREATE,
        UPDATE,
        DELETE
    }

    public enum EntityType {
        MEMBER,
        STORE,
        POS,
        DRIVER
    }
}
