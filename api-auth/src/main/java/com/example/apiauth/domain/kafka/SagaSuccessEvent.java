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
public class SagaSuccessEvent {
    private String entityType;
    private Integer entityId;
    private LocalDateTime syncedAt;

    public static SagaSuccessEvent of(String entityType, Integer entityId) {
        return SagaSuccessEvent.builder()
                .entityType(entityType)
                .entityId(entityId)
                .syncedAt(LocalDateTime.now())
                .build();
    }
}
