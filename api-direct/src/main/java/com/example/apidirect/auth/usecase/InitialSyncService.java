package com.example.apidirect.auth.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.apidirect.auth.adapter.out.external.MonolithSyncClient;
import com.example.apidirect.auth.adapter.out.external.dto.PosSyncDto;
import com.example.apidirect.auth.adapter.out.external.dto.StoreSyncDto;
import com.example.apidirect.auth.adapter.out.persistence.POSRepository;
import com.example.apidirect.auth.adapter.out.persistence.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitialSyncService {

    private final MonolithSyncClient monolithSyncClient;
    private final StoreRepository storeRepository;
    private final POSRepository posRepository;
    private final SyncStatusService syncStatusService;

    @Transactional
    public Map<String, Integer> syncAll() {
        log.info("[SYNC] Starting initial data synchronization from Monolith...");

        Map<String, Integer> syncResults = new LinkedHashMap<>();

        try {
            syncResults.put("stores", syncStores());
            syncResults.put("pos", syncPos());

            syncStatusService.markAsSynced();
            log.info("[SYNC] Initial data synchronization completed: {}", syncResults);

        } catch (Exception e) {
            log.error("[SYNC] Initial data synchronization failed", e);
            throw new RuntimeException("Failed to sync initial data from Monolith", e);
        }

        return syncResults;
    }

    @Transactional
    public int syncStores() {
        log.info("[SYNC] Fetching stores from Monolith...");
        List<StoreSyncDto> stores = monolithSyncClient.getAllStores();

        log.info("[SYNC] Saving {} stores to database...", stores.size());
        for (StoreSyncDto dto : stores) {
            storeRepository.upsertStore(
                    dto.getId(),
                    dto.getLat(),
                    dto.getLon(),
                    dto.getPosCount(),
                    dto.getStoreNumber(),
                    dto.getStoreState(),
                    dto.getMemberId(),
                    dto.getCreatedAt(),
                    dto.getUpdatedAt()
            );
        }

        log.info("[SYNC] Successfully saved {} stores", stores.size());
        return stores.size();
    }

    @Transactional
    public int syncPos() {
        log.info("[SYNC] Fetching POS from Monolith...");
        List<PosSyncDto> posList = monolithSyncClient.getAllPos();

        log.info("[SYNC] Saving {} POS to database...", posList.size());
        for (PosSyncDto dto : posList) {
            posRepository.upsertPos(
                    dto.getId(),
                    dto.getPosCode(),
                    dto.getStoreId(),
                    dto.getHealthCheck(),
                    dto.getMemberId(),
                    dto.getCreatedAt(),
                    dto.getUpdatedAt()
            );
        }

        log.info("[SYNC] Successfully saved {} POS", posList.size());
        return posList.size();
    }

    public boolean isAlreadySynced() {
        return syncStatusService.isSynced();
    }
}
