package MeshX.HypeLink.auth.controller;

import MeshX.HypeLink.auth.model.dto.sync.DriverSyncDto;
import MeshX.HypeLink.auth.model.dto.sync.MemberSyncDto;
import MeshX.HypeLink.auth.model.dto.sync.PosSyncDto;
import MeshX.HypeLink.auth.model.dto.sync.StoreSyncDto;
import MeshX.HypeLink.auth.repository.DriverRepository;
import MeshX.HypeLink.auth.repository.MemberRepository;
import MeshX.HypeLink.auth.repository.PosRepository;
import MeshX.HypeLink.auth.repository.StoreRepository;
import MeshX.HypeLink.head_office.item.service.ItemSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/internal/sync")
@RequiredArgsConstructor
public class InternalSyncController {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final PosRepository posRepository;
    private final DriverRepository driverRepository;
    private final ItemSyncService itemSyncService;

    @GetMapping("/members")
    public List<MemberSyncDto> getAllMembers() {
        log.info("Fetching all members for sync");
        return memberRepository.findAll().stream()
                .map(MemberSyncDto::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/stores")
    public List<StoreSyncDto> getAllStores() {
        log.info("Fetching all stores for sync");
        return storeRepository.findAll().stream()
                .map(StoreSyncDto::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/pos")
    public List<PosSyncDto> getAllPos() {
        log.info("Fetching all pos for sync");
        return posRepository.findAll().stream()
                .map(PosSyncDto::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/drivers")
    public List<DriverSyncDto> getAllDrivers() {
        log.info("Fetching all drivers for sync");
        return driverRepository.findAll().stream()
                .map(DriverSyncDto::from)
                .collect(Collectors.toList());
    }

    @PostMapping("/store/{storeId}/items")
    public void syncAllItemsToStore(@PathVariable Integer storeId) {
        log.info("Syncing all items to store ID: {}", storeId);
        itemSyncService.syncAllItemsForNewStore(storeId);
    }

}
