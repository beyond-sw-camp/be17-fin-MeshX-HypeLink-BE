package com.example.apiauth.adapter.in.web;

import MeshX.common.WebAdapter;
import com.example.apiauth.adapter.in.web.dto.SyncStatusResDto;
import com.example.apiauth.adapter.out.persistence.SyncStatusRepository;
import com.example.apiauth.adapter.out.persistence.entity.SyncStatusEntity;
import com.example.apiauth.usecase.InitialSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@WebAdapter
@RestController
@RequestMapping("/admin/sync")
@RequiredArgsConstructor
public class InitialSyncController {

    private final InitialSyncService initialSyncService;
    private final SyncStatusRepository syncStatusRepository;

    /**
     * 동기화 상태 조회
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getSyncStatus() {
        log.info("Getting sync status...");

        List<SyncStatusResDto> statusList = new ArrayList<>();

        // Member
        SyncStatusEntity memberStatus = syncStatusRepository.findByEntityType("MEMBER").orElse(null);
        statusList.add(memberStatus != null
                ? SyncStatusResDto.from(memberStatus)
                : SyncStatusResDto.notSynced("MEMBER"));

        // Store
        SyncStatusEntity storeStatus = syncStatusRepository.findByEntityType("STORE").orElse(null);
        statusList.add(storeStatus != null
                ? SyncStatusResDto.from(storeStatus)
                : SyncStatusResDto.notSynced("STORE"));

        // POS
        SyncStatusEntity posStatus = syncStatusRepository.findByEntityType("POS").orElse(null);
        statusList.add(posStatus != null
                ? SyncStatusResDto.from(posStatus)
                : SyncStatusResDto.notSynced("POS"));

        // Driver
        SyncStatusEntity driverStatus = syncStatusRepository.findByEntityType("DRIVER").orElse(null);
        statusList.add(driverStatus != null
                ? SyncStatusResDto.from(driverStatus)
                : SyncStatusResDto.notSynced("DRIVER"));

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "entities", statusList
        ));
    }

    /**
     * 전체 엔티티 초기 동기화 트리거
     */
    @PostMapping("/trigger-all")
    public ResponseEntity<Map<String, Object>> triggerSyncAll() {
        log.info("Triggering initial sync for all entities...");

        // 이미 동기화됐는지 확인
        if (initialSyncService.isAlreadySynced()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "Already synced. Use /force-sync-all to re-sync"
            ));
        }

        try {
            Map<String, Integer> result = initialSyncService.syncAll();

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Initial sync completed",
                    "syncedCounts", result
            ));
        } catch (Exception e) {
            log.error("Failed to sync all entities", e);
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Sync failed: " + e.getMessage()
            ));
        }
    }

    /**
     * 강제 재동기화 (데이터 손실 시)
     */
    @PostMapping("/force-sync-all")
    public ResponseEntity<Map<String, Object>> forceSyncAll() {
        log.warn("Force syncing all entities...");

        try {
            // sync_status 초기화
            syncStatusRepository.deleteAll();

            Map<String, Integer> result = initialSyncService.syncAll();

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Force sync completed",
                    "syncedCounts", result
            ));
        } catch (Exception e) {
            log.error("Failed to force sync all entities", e);
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Force sync failed: " + e.getMessage()
            ));
        }
    }

    /**
     * Member만 동기화
     */
    @PostMapping("/members")
    public ResponseEntity<Map<String, Object>> syncMembers() {
        log.info("Syncing members only...");

        try {
            int count = initialSyncService.syncMembers();
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Members synced",
                    "count", count
            ));
        } catch (Exception e) {
            log.error("Failed to sync members", e);
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Members sync failed: " + e.getMessage()
            ));
        }
    }

    /**
     * Store만 동기화
     */
    @PostMapping("/stores")
    public ResponseEntity<Map<String, Object>> syncStores() {
        log.info("Syncing stores only...");

        try {
            int count = initialSyncService.syncStores();
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Stores synced",
                    "count", count
            ));
        } catch (Exception e) {
            log.error("Failed to sync stores", e);
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Stores sync failed: " + e.getMessage()
            ));
        }
    }

    /**
     * POS만 동기화
     */
    @PostMapping("/pos")
    public ResponseEntity<Map<String, Object>> syncPos() {
        log.info("Syncing POS only...");

        try {
            int count = initialSyncService.syncPos();
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "POS synced",
                    "count", count
            ));
        } catch (Exception e) {
            log.error("Failed to sync POS", e);
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "POS sync failed: " + e.getMessage()
            ));
        }
    }

    /**
     * Driver만 동기화
     */
    @PostMapping("/drivers")
    public ResponseEntity<Map<String, Object>> syncDrivers() {
        log.info("Syncing drivers only...");

        try {
            int count = initialSyncService.syncDrivers();
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Drivers synced",
                    "count", count
            ));
        } catch (Exception e) {
            log.error("Failed to sync drivers", e);
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Drivers sync failed: " + e.getMessage()
            ));
        }
    }
}
