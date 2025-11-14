package org.example.apidirect.auth.usecase;

import lombok.RequiredArgsConstructor;
import org.example.apidirect.auth.adapter.out.entity.SyncStatusEntity;
import org.example.apidirect.auth.adapter.out.persistence.SyncStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SyncStatusService {

    private static final String SYNC_TYPE = "INITIAL_SYNC";

    private final SyncStatusRepository syncStatusRepository;

    @Transactional(readOnly = true)
    public boolean isSynced() {
        return syncStatusRepository.findBySyncType(SYNC_TYPE)
                .map(SyncStatusEntity::getCompleted)
                .orElse(false);
    }

    @Transactional
    public void markAsSynced() {
        SyncStatusEntity syncStatus = syncStatusRepository.findBySyncType(SYNC_TYPE)
                .orElseGet(() -> SyncStatusEntity.builder()
                        .syncType(SYNC_TYPE)
                        .completed(false)
                        .syncedAt(LocalDateTime.now())
                        .build());

        syncStatus.markAsCompleted();
        syncStatusRepository.save(syncStatus);
    }
}
