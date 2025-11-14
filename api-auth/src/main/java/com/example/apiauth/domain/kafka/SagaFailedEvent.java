package com.example.apiauth.domain.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SagaFailedEvent {
    private String entityType;
    private Integer entityId;
    private String errorMessage;
    private LocalDateTime failedAt;
    private LocalDateTime retryAt;
    private MemberRegisterEvent originalEvent;

    public static SagaFailedEvent of(String entityType, Integer entityId, String errorMessage, MemberRegisterEvent originalEvent) {
        LocalDateTime now = LocalDateTime.now();
        return SagaFailedEvent.builder()
                .entityType(entityType)
                .entityId(entityId)
                .errorMessage(errorMessage)
                .failedAt(now)
                .retryAt(now.plusMinutes(5))  // 5분 후 재시도
                .originalEvent(originalEvent)
                .build();
    }
}
