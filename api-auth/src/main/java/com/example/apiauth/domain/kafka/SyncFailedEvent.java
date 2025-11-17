package com.example.apiauth.domain.kafka;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SyncFailedEvent {
    private String entityType;
    private Integer entityId;
    private String errorMessage;
    private LocalDateTime failedAt;
    private LocalDateTime retryAt;
    private MemberRegisterEvent originalEvent;

    public static SyncFailedEvent of(String EntityType, Integer EntityId, String errorMessage, MemberRegisterEvent originalEvent) {
        return SyncFailedEvent.builder()
                .entityType(EntityType)
                .entityId(EntityId)
                .failedAt(LocalDateTime.now())
                .retryAt(LocalDateTime.now().plusMinutes(5))
                .errorMessage(errorMessage)
                .originalEvent(originalEvent)
                .build();
    }

}
