package com.example.apidirect.auth.adapter.out.external;

import com.example.apidirect.auth.adapter.out.external.dto.PosSyncDto;
import com.example.apidirect.auth.adapter.out.external.dto.StoreSyncDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "monolith-sync", url = "${head-office.service.url}")
public interface MonolithSyncClient {

    @GetMapping("/internal/sync/stores")
    List<StoreSyncDto> getAllStores();

    @GetMapping("/internal/sync/pos")
    List<PosSyncDto> getAllPos();
}
