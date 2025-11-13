package com.example.apiauth.adapter.in.web.dto;

import com.example.apiauth.adapter.out.persistence.entity.SyncStatusEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncStatusResDto {
    private String entityType;
    private Boolean synced;
    private Integer count;
    private LocalDateTime lastSync;

    public static SyncStatusResDto from(SyncStatusEntity entity) {
        return SyncStatusResDto.builder()
                .entityType(entity.getEntityType())
                .synced(entity.getIsInitialSyncCompleted())
                .count(entity.getTotalSyncedCount())
                .lastSync(entity.getLastSyncAt())
                .build();
    }

    public static SyncStatusResDto notSynced(String entityType) {
        return SyncStatusResDto.builder()
                .entityType(entityType)
                .synced(false)
                .count(0)
                .lastSync(null)
                .build();
    }
}
