package com.example.apiauth.adapter.in.runner;

import com.example.apiauth.usecase.InitialSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialDataSyncRunner implements CommandLineRunner {

    private final InitialSyncService initialSyncService;

    @Override
    public void run(String... args) {
        try {
            log.info("=".repeat(80));
            log.info("Checking initial data sync status...");

            // 이미 동기화됐는지 확인
            if (initialSyncService.isAlreadySynced()) {
                log.info("Initial data sync already completed. Skipping...");
                log.info("=".repeat(80));
                return;
            }

            log.info("Starting initial data sync from monolith...");
            log.info("=".repeat(80));

            // 초기 동기화 실행
            Map<String, Integer> syncResults = initialSyncService.syncAll();

            log.info("=".repeat(80));
            log.info("Initial data sync completed successfully!");
            log.info("Sync results: {}", syncResults);
            log.info("  - Members: {} records", syncResults.get("members"));
            log.info("  - Stores: {} records", syncResults.get("stores"));
            log.info("  - POS: {} records", syncResults.get("pos"));
            log.info("  - Drivers: {} records", syncResults.get("drivers"));
            log.info("=".repeat(80));

        } catch (Exception e) {
            log.error("=".repeat(80));
            log.error("Failed to perform initial data sync", e);
            log.error("Server will continue to run, but data may be incomplete!");
            log.error("You can manually trigger sync by calling: POST /admin/sync/trigger-all");
            log.error("=".repeat(80));
        }
    }
}
