package org.example.apidirect.auth.adapter.in.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apidirect.auth.usecase.InitialSyncService;
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
            if (initialSyncService.isAlreadySynced()) {
                log.info("=================================================");
                log.info("Initial data sync already completed. Skipping...");
                log.info("=================================================");
                return;
            }

            log.info("=================================================");
            log.info("Starting initial data synchronization from Monolith");
            log.info("=================================================");

            Map<String, Integer> syncResults = initialSyncService.syncAll();

            log.info("=================================================");
            log.info("Initial data sync completed successfully!");
            log.info("Sync Results:");
            syncResults.forEach((key, value) ->
                    log.info("  - {}: {} records", key, value));
            log.info("=================================================");

        } catch (Exception e) {
            log.error("=================================================");
            log.error("Initial data sync FAILED!");
            log.error("Error: {}", e.getMessage(), e);
            log.error("You can manually trigger sync by calling:");
            log.error("POST /api/admin/sync/trigger-all");
            log.error("=================================================");
        }
    }
}
