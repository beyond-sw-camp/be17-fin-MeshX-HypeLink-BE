package MeshX.HypeLink.auth.controller;

import MeshX.HypeLink.auth.constansts.AuthSwaggerConstants;
import MeshX.HypeLink.auth.model.dto.sync.DriverSyncDto;
import MeshX.HypeLink.auth.model.dto.sync.MemberSyncDto;
import MeshX.HypeLink.auth.model.dto.sync.PosSyncDto;
import MeshX.HypeLink.auth.model.dto.sync.StoreSyncDto;
import MeshX.HypeLink.auth.repository.DriverRepository;
import MeshX.HypeLink.auth.repository.MemberRepository;
import MeshX.HypeLink.auth.repository.PosRepository;
import MeshX.HypeLink.auth.repository.StoreRepository;
import MeshX.HypeLink.head_office.item.service.ItemSyncService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "내부 데이터 동기화", description = "마이크로서비스 간 데이터 동기화를 위한 내부 API (회원, 매장, POS, 드라이버, 상품 정보)")
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

    @Operation(summary = "모든 회원 정보 동기화", description = "마이크로서비스 간 데이터 동기화를 위해 모든 회원 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MemberSyncDto.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.MEMBER_SYNC_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/members")
    public List<MemberSyncDto> getAllMembers() {
        log.info("Fetching all members for sync");
        return memberRepository.findAll().stream()
                .map(MemberSyncDto::from)
                .collect(Collectors.toList());
    }

    @Operation(summary = "모든 매장 정보 동기화", description = "마이크로서비스 간 데이터 동기화를 위해 모든 매장 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "매장 정보 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StoreSyncDto.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.STORE_SYNC_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/stores")
    public List<StoreSyncDto> getAllStores() {
        log.info("Fetching all stores for sync");
        return storeRepository.findAll().stream()
                .map(StoreSyncDto::from)
                .collect(Collectors.toList());
    }

    @Operation(summary = "모든 POS 정보 동기화", description = "마이크로서비스 간 데이터 동기화를 위해 모든 POS 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "POS 정보 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PosSyncDto.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.POS_SYNC_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/pos")
    public List<PosSyncDto> getAllPos() {
        log.info("Fetching all pos for sync");
        return posRepository.findAll().stream()
                .map(PosSyncDto::from)
                .collect(Collectors.toList());
    }

    @Operation(summary = "모든 드라이버 정보 동기화", description = "마이크로서비스 간 데이터 동기화를 위해 모든 드라이버 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "드라이버 정보 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DriverSyncDto.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.DRIVER_SYNC_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/drivers")
    public List<DriverSyncDto> getAllDrivers() {
        log.info("Fetching all drivers for sync");
        return driverRepository.findAll().stream()
                .map(DriverSyncDto::from)
                .collect(Collectors.toList());
    }

    @Operation(summary = "매장 상품 정보 동기화", description = "특정 매장에 모든 상품 정보를 동기화합니다. 신규 매장 생성 시 본사의 상품 정보를 동기화할 때 사용됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 동기화 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = AuthSwaggerConstants.SYNC_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "해당 ID의 매장을 찾을 수 없습니다.", content = @Content)
    })
    @PostMapping("/store/{storeId}/items")
    public void syncAllItemsToStore(
            @Parameter(description = "매장 ID", required = true)
            @PathVariable Integer storeId) {
        log.info("Syncing all items to store ID: {}", storeId);
        itemSyncService.syncAllItemsForNewStore(storeId);
    }

}
