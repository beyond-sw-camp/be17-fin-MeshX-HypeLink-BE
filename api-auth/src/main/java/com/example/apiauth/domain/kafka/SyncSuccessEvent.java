package com.example.apiauth.domain.kafka;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SyncSuccessEvent {
    private String entityType;
    private Integer entityId;
    private LocalDateTime syncedAt;

    public static SyncSuccessEvent of(String entityType, Integer entityId){
        return SyncSuccessEvent.builder()
                .entityType(entityType)
                .entityId(entityId)
                .syncedAt(LocalDateTime.now())
                .build();
    }

}
