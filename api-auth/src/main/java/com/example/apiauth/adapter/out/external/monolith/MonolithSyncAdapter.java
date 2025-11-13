package com.example.apiauth.adapter.out.external.monolith;

import com.example.apiauth.adapter.out.external.monolith.dto.DriverSyncDto;
import com.example.apiauth.adapter.out.external.monolith.dto.MemberSyncDto;
import com.example.apiauth.adapter.out.external.monolith.dto.PosSyncDto;
import com.example.apiauth.adapter.out.external.monolith.dto.StoreSyncDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * MonolithSyncClient를 감싸서 Circuit Breaker와 Retry 적용
 * Monolith 서비스 장애 시 Fallback으로 빈 리스트 반환
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MonolithSyncAdapter {

    private final MonolithSyncClient monolithSyncClient;

    @CircuitBreaker(name = "monolithSync", fallbackMethod = "fallbackGetAllMembers")
    @Retry(name = "monolithSync")
    public List<MemberSyncDto> getAllMembers() {
        log.info("Fetching all members from monolith...");
        return monolithSyncClient.getAllMembers();
    }

    @CircuitBreaker(name = "monolithSync", fallbackMethod = "fallbackGetAllStores")
    @Retry(name = "monolithSync")
    public List<StoreSyncDto> getAllStores() {
        log.info("Fetching all stores from monolith...");
        return monolithSyncClient.getAllStores();
    }

    @CircuitBreaker(name = "monolithSync", fallbackMethod = "fallbackGetAllPos")
    @Retry(name = "monolithSync")
    public List<PosSyncDto> getAllPos() {
        log.info("Fetching all POS from monolith...");
        return monolithSyncClient.getAllPos();
    }

    @CircuitBreaker(name = "monolithSync", fallbackMethod = "fallbackGetAllDrivers")
    @Retry(name = "monolithSync")
    public List<DriverSyncDto> getAllDrivers() {
        log.info("Fetching all drivers from monolith...");
        return monolithSyncClient.getAllDrivers();
    }

    // Fallback methods
    private List<MemberSyncDto> fallbackGetAllMembers(Exception e) {
        log.error("Circuit breaker activated for getAllMembers. Monolith service unavailable. Reason: {}", e.getMessage());
        return Collections.emptyList();
    }

    private List<StoreSyncDto> fallbackGetAllStores(Exception e) {
        log.error("Circuit breaker activated for getAllStores. Monolith service unavailable. Reason: {}", e.getMessage());
        return Collections.emptyList();
    }

    private List<PosSyncDto> fallbackGetAllPos(Exception e) {
        log.error("Circuit breaker activated for getAllPos. Monolith service unavailable. Reason: {}", e.getMessage());
        return Collections.emptyList();
    }

    private List<DriverSyncDto> fallbackGetAllDrivers(Exception e) {
        log.error("Circuit breaker activated for getAllDrivers. Monolith service unavailable. Reason: {}", e.getMessage());
        return Collections.emptyList();
    }
}
