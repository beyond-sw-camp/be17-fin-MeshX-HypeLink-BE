package com.example.apidirect.auth.adapter.out.external;

import com.example.apidirect.auth.adapter.out.external.dto.CustomerReceiptSyncDto;
import com.example.apidirect.auth.adapter.out.external.dto.PosSyncDto;
import com.example.apidirect.auth.adapter.out.external.dto.StoreSyncDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "monolith-sync", url = "${head-office.service.url}")
public interface MonolithSyncClient {

    @GetMapping("/internal/sync/stores")
    List<StoreSyncDto> getAllStores();

    @GetMapping("/internal/sync/pos")
    List<PosSyncDto> getAllPos();

    @PostMapping("/internal/sync/store/{storeId}/items")
    void syncStoreItems(@PathVariable("storeId") Integer storeId);

    @GetMapping("/internal/sync/receipts")
    Page<CustomerReceiptSyncDto> getReceipts(@RequestParam("page") int page, @RequestParam("size") int size);
}
