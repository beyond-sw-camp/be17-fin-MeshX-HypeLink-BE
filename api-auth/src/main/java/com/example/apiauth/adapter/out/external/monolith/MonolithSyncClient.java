package com.example.apiauth.adapter.out.external.monolith;

import com.example.apiauth.adapter.out.external.monolith.dto.DriverSyncDto;
import com.example.apiauth.adapter.out.external.monolith.dto.MemberSyncDto;
import com.example.apiauth.adapter.out.external.monolith.dto.PosSyncDto;
import com.example.apiauth.adapter.out.external.monolith.dto.StoreSyncDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "monolith-sync", url = "${monolith.url}")
public interface MonolithSyncClient {

    @GetMapping("/internal/sync/members")
    List<MemberSyncDto> getAllMembers();

    @GetMapping("/internal/sync/stores")
    List<StoreSyncDto> getAllStores();

    @GetMapping("/internal/sync/pos")
    List<PosSyncDto> getAllPos();

    @GetMapping("/internal/sync/drivers")
    List<DriverSyncDto> getAllDrivers();
}
