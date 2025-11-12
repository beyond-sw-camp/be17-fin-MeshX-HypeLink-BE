package com.example.apiauth.usecase;

import com.example.apiauth.adapter.out.external.monolith.MonolithSyncClient;
import com.example.apiauth.adapter.out.external.monolith.dto.DriverSyncDto;
import com.example.apiauth.adapter.out.external.monolith.dto.MemberSyncDto;
import com.example.apiauth.adapter.out.external.monolith.dto.PosSyncDto;
import com.example.apiauth.adapter.out.external.monolith.dto.StoreSyncDto;
import com.example.apiauth.adapter.out.persistence.InitialSyncRepository;
import com.example.apiauth.adapter.out.persistence.SyncStatusRepository;
import com.example.apiauth.adapter.out.persistence.entity.SyncStatusEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitialSyncService {

    private final MonolithSyncClient monolithSyncClient;
    private final InitialSyncRepository initialSyncRepository;
    private final SyncStatusRepository syncStatusRepository;

    public boolean isAlreadySynced() {
        return syncStatusRepository.findByEntityType("MEMBER")
                .map(SyncStatusEntity::getIsInitialSyncCompleted)
                .orElse(false);
    }

    @Transactional
    public Map<String, Integer> syncAll() {
        log.info("Starting initial data sync from monolith...");

        Map<String, Integer> syncResults = new HashMap<>();

        syncResults.put("members", syncMembers());
        syncResults.put("stores", syncStores());
        syncResults.put("pos", syncPos());
        syncResults.put("drivers", syncDrivers());

        log.info("Initial data sync completed: {}", syncResults);
        return syncResults;
    }

    @Transactional
    public int syncMembers() {
        Optional<SyncStatusEntity> status = syncStatusRepository.findByEntityType("MEMBER");
        if (status.isPresent() && status.get().getIsInitialSyncCompleted()) {
            log.info("Members already synced. Skipping...");
            return 0;
        }

        log.info("Syncing members from monolith...");
        List<MemberSyncDto> members = monolithSyncClient.getAllMembers();

        if (members.isEmpty()) {
            log.warn("No members to sync");
            return 0;
        }

        // JDBC Batch Insert (Write DB)
        initialSyncRepository.batchInsertMembers(members);

        // Read DB 직접 동기화
        initialSyncRepository.batchInsertMembersToReadDB(members);

        // sync_status 업데이트
        SyncStatusEntity syncStatus = SyncStatusEntity.builder()
                .entityType("MEMBER")
                .lastSyncAt(LocalDateTime.now())
                .totalSyncedCount(members.size())
                .isInitialSyncCompleted(true)
                .build();
        syncStatusRepository.save(syncStatus);

        log.info("Members sync completed: {} records", members.size());
        return members.size();
    }

    @Transactional
    public int syncStores() {
        Optional<SyncStatusEntity> status = syncStatusRepository.findByEntityType("STORE");
        if (status.isPresent() && status.get().getIsInitialSyncCompleted()) {
            log.info("Stores already synced. Skipping...");
            return 0;
        }

        log.info("Syncing stores from monolith...");
        List<StoreSyncDto> stores = monolithSyncClient.getAllStores();

        if (stores.isEmpty()) {
            log.warn("No stores to sync");
            return 0;
        }

        // JDBC Batch Insert (Write DB)
        initialSyncRepository.batchInsertStores(stores);

        // Read DB 직접 동기화
        initialSyncRepository.batchInsertStoresToReadDB(stores);

        // sync_status 업데이트
        SyncStatusEntity syncStatus = SyncStatusEntity.builder()
                .entityType("STORE")
                .lastSyncAt(LocalDateTime.now())
                .totalSyncedCount(stores.size())
                .isInitialSyncCompleted(true)
                .build();
        syncStatusRepository.save(syncStatus);

        log.info("Stores sync completed: {} records", stores.size());
        return stores.size();
    }

    @Transactional
    public int syncPos() {
        Optional<SyncStatusEntity> status = syncStatusRepository.findByEntityType("POS");
        if (status.isPresent() && status.get().getIsInitialSyncCompleted()) {
            log.info("POS already synced. Skipping...");
            return 0;
        }

        log.info("Syncing POS from monolith...");
        List<PosSyncDto> posList = monolithSyncClient.getAllPos();

        if (posList.isEmpty()) {
            log.warn("No pos to sync");
            return 0;
        }

        // JDBC Batch Insert (Write DB)
        initialSyncRepository.batchInsertPos(posList);

        // Read DB 직접 동기화
        initialSyncRepository.batchInsertPosToReadDB(posList);

        // sync_status 업데이트
        SyncStatusEntity syncStatus = SyncStatusEntity.builder()
                .entityType("POS")
                .lastSyncAt(LocalDateTime.now())
                .totalSyncedCount(posList.size())
                .isInitialSyncCompleted(true)
                .build();
        syncStatusRepository.save(syncStatus);

        log.info("POS sync completed: {} records", posList.size());
        return posList.size();
    }

    @Transactional
    public int syncDrivers() {
        Optional<SyncStatusEntity> status = syncStatusRepository.findByEntityType("DRIVER");
        if (status.isPresent() && status.get().getIsInitialSyncCompleted()) {
            log.info("Drivers already synced. Skipping...");
            return 0;
        }

        log.info("Syncing drivers from monolith...");
        List<DriverSyncDto> drivers = monolithSyncClient.getAllDrivers();

        if (drivers.isEmpty()) {
            log.warn("No drivers to sync");
            return 0;
        }

        // JDBC Batch Insert (Write DB)
        initialSyncRepository.batchInsertDrivers(drivers);

        // Read DB 직접 동기화
        initialSyncRepository.batchInsertDriversToReadDB(drivers);

        // sync_status 업데이트
        SyncStatusEntity syncStatus = SyncStatusEntity.builder()
                .entityType("DRIVER")
                .lastSyncAt(LocalDateTime.now())
                .totalSyncedCount(drivers.size())
                .isInitialSyncCompleted(true)
                .build();
        syncStatusRepository.save(syncStatus);

        log.info("Drivers sync completed: {} records", drivers.size());
        return drivers.size();
    }
}
