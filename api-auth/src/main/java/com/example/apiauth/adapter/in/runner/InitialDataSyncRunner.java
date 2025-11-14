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

            // 동기화된 데이터가 하나도 없으면 실패로 간주
            int totalSynced = syncResults.values().stream().mapToInt(Integer::intValue).sum();
            if (totalSynced == 0) {
                log.error("=".repeat(80));
                log.error("FATAL: Initial data sync FAILED!");
                log.error("No data was synced from Monolith.");
                log.error("api-auth cannot start without initial data.");
                log.error("Please check if Monolith service is running at: {}", System.getenv("MONOLITH_URL"));
                log.error("=".repeat(80));
                throw new RuntimeException("Initial data sync failed - no data received from Monolith");
            }

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
            log.error("FATAL: Initial data sync failed!", e);
            log.error("api-auth service cannot start without initial data.");
            log.error("Shutting down application...");
            log.error("=".repeat(80));
            // 애플리케이션 종료
            System.exit(1);
        }
    }
}
