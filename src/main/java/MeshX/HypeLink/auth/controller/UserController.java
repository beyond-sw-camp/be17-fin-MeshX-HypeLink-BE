package MeshX.HypeLink.auth.controller;

import MeshX.HypeLink.auth.constansts.AuthSwaggerConstants;
import MeshX.HypeLink.auth.model.dto.req.DriverListReqDto;
import MeshX.HypeLink.auth.model.dto.req.StoreStateReqDto;
import MeshX.HypeLink.auth.model.dto.res.*;
import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.service.MemberService;
import MeshX.HypeLink.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "사용자 및 매장 관리", description = "사용자 정보, 매장 정보, 드라이버 정보 등을 조회하고 관리하는 API")
@Slf4j
@RestController
@RequestMapping("/api/member")
@AllArgsConstructor
public class UserController {
    private final MemberService memberService;

    @Operation(summary = "전체 사용자 목록 조회", description = "시스템에 등록된 모든 사용자의 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.USER_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/member/list")
    public ResponseEntity<BaseResponse<UserListResDto>> list() {
        UserListResDto result = memberService.list();
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "드라이버 목록 조회", description = "시스템에 등록된 모든 드라이버의 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "드라이버 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.DRIVER_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/driver/list")
    public ResponseEntity<BaseResponse<List<DriverListReqDto>>> driverList() {
        List<DriverListReqDto> result = memberService.dirverList();

        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "매장 및 POS 목록 조회", description = "시스템에 등록된 모든 매장과 해당 매장의 POS 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "매장 및 POS 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.STORE_WITH_POS_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/storepos/list")
    public ResponseEntity<BaseResponse<List<StoreWithPosResDto>>> storeWithPosList() {
        List<StoreWithPosResDto> result = memberService.storeWithPosList();

        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "매장 목록 조회", description = "시스템에 등록된 모든 매장의 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "매장 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.STORE_LIST_RES_EXAMPLE)))
    })
    @GetMapping("/store/list")
    public ResponseEntity<BaseResponse<Page<StoreListResDto>>> storeList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(defaultValue = "") String keyWord,
            @RequestParam(defaultValue = "all") String status) {
        Page<StoreListResDto> result = memberService.storeList(pageable, keyWord, status);

        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "내 매장 정보 조회", description = "현재 로그인된 사용자의 매장 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 매장 정보 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.STORE_WITH_POS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "매장 정보를 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/mystore/read")
    public ResponseEntity<BaseResponse<StoreWithPosResDto>> readMyStore(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        Member member = memberService.findMember( userDetails.getUsername());
        StoreWithPosResDto result = memberService.readMyStore(member);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "다른 매장 정보 조회", description = "특정 매장 ID를 사용하여 다른 매장의 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "다른 매장 정보 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.STORE_WITH_POS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "해당 ID의 매장을 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/otherstore/read/{id}")
    public ResponseEntity<BaseResponse<StoreWithPosResDto>> readOtherStore(
            @Parameter(description = "조회할 매장의 ID") @PathVariable Integer id) {
        StoreWithPosResDto result = memberService.readOtherStroe(id);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "내 매장 ID 조회", description = "현재 로그인된 사용자의 매장 ID를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 매장 ID 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.MY_STORE_ID_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "매장 ID를 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/mystoreid")
    public ResponseEntity<BaseResponse<Integer>> getMyStoreId(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        Integer result = memberService.getMyStoreId(userDetails);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "매장 상세 정보 조회", description = "특정 매장 ID를 사용하여 매장의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "매장 상세 정보 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.STORE_INFO_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "해당 ID의 매장을 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/storeinfo/read/{id}")
    public ResponseEntity<BaseResponse<StoreInfoResDto>> readStoreInfo(
            @Parameter(description = "조회할 매장의 ID") @PathVariable Integer id) {
        StoreInfoResDto result = memberService.readStoreInfo(id);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "매장 정보 수정", description = "특정 매장 ID를 사용하여 매장 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "매장 정보 수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.UPDATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 ID의 매장을 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/store/{id}")
    public ResponseEntity<BaseResponse<String>> updateStore(
            @Parameter(description = "수정할 매장의 ID") @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "매장 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StoreInfoResDto.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.STORE_INFO_RES_EXAMPLE)
                    )
            )
            @RequestBody StoreInfoResDto dto) {
        memberService.updateStoreInfo(id, dto);
        return ResponseEntity.ok(BaseResponse.of("매장 정보가 성공적으로 수정되었습니다."));
    }

    @Operation(summary = "매장 상태 수정", description = "특정 매장 ID를 사용하여 매장의 운영 상태를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "매장 상태 수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.UPDATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 ID의 매장을 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/store/state/{id}")
    public ResponseEntity<BaseResponse<String>> readStoreState(
            @Parameter(description = "상태를 수정할 매장의 ID") @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "매장 상태 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StoreStateReqDto.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.STORE_STATE_REQ_EXAMPLE)
                    )
            )
            @RequestBody StoreStateReqDto dto) {
        memberService.storeStateUpdate(id,dto);

        return ResponseEntity.ok(BaseResponse.of("성공적으로 변경하였습니다."));
    }

    @Operation(summary = "사용자 상세 정보 조회", description = "특정 사용자 ID를 사용하여 사용자 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 상세 정보 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.USER_READ_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "해당 ID의 사용자를 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/user/read/{id}")
    public ResponseEntity<BaseResponse<UserReadResDto>> readUser(
            @Parameter(description = "조회할 사용자의 ID") @PathVariable Integer id) {
        UserReadResDto result = memberService.userRead(id);

        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "사용자 정보 수정", description = "특정 사용자 ID를 사용하여 사용자 정보를 수정합니다. ADMIN 또는 MANAGER 권한이 필요합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.UPDATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 ID의 사용자를 찾을 수 없습니다.", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PatchMapping("/user/update/{id}")
    public ResponseEntity<BaseResponse<String>> updateUser(
            @Parameter(description = "수정할 사용자의 ID") @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "사용자 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadResDto.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.USER_READ_RES_EXAMPLE)
                    )
            )
            @RequestBody UserReadResDto dto) {
        memberService.updateUser(id,dto);

        return ResponseEntity.ok(BaseResponse.of("성공적으로 변경하였습니다."));
    }

    @Operation(summary = "사용자 삭제", description = "특정 사용자 ID를 사용하여 사용자를 삭제합니다. ADMIN 권한이 필요합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 삭제 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.DELETE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 ID의 사용자를 찾을 수 없습니다.", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteMember(
            @Parameter(description = "삭제할 사용자의 ID") @PathVariable Integer id) {
        memberService.deleteUser(id);

        return ResponseEntity.ok(BaseResponse.of("사용자가 성공적으로 삭제되었습니다."));
    }

    @Operation(summary = "매장 삭제", description = "특정 매장 ID를 사용하여 매장을 삭제합니다. ADMIN 또는 MANAGER 권한이 필요합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "매장 삭제 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.DELETE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 ID의 매장을 찾을 수 없습니다.", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/store/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteStore(
            @Parameter(description = "삭제할 매장의 ID") @PathVariable Integer id) {
        memberService.deleteStore(id);

        return ResponseEntity.ok(BaseResponse.of("사용자가 성공적으로 삭제되었습니다."));
    }

    @Operation(summary = "POS 삭제", description = "특정 POS ID를 사용하여 POS를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "POS 삭제 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.DELETE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "해당 ID의 POS를 찾을 수 없습니다.", content = @Content)
    })
    @DeleteMapping("/pos/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deletePos(
            @Parameter(description = "삭제할 POS의 ID") @PathVariable Integer id) {
        memberService.deletePos(id);
        return ResponseEntity.ok(BaseResponse.of("사용자가 성공적으로 삭제되었습니다."));
    }

    @Operation(summary = "드라이버 삭제", description = "특정 드라이버 ID를 사용하여 드라이버를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "드라이버 삭제 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.DELETE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "해당 ID의 드라이버를 찾을 수 없습니다.", content = @Content)
    })
    @DeleteMapping("/driver/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteDriver(
            @Parameter(description = "삭제할 드라이버의 ID") @PathVariable Integer id) {
        memberService.deleteDriver(id);
        return ResponseEntity.ok(BaseResponse.of("사용자가 성공적으로 삭제되었습니다."));
    }

    @Operation(summary = "매장 주소 정보 목록 조회", description = "모든 매장의 주소 정보를 조회합니다. ADMIN 또는 MANAGER 권한이 필요합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "매장 주소 정보 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.STORE_ADD_INFO_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다.", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/store/list/address")
    public ResponseEntity<BaseResponse<StoreAddInfoListRes>> getMyStoreId() {
        StoreAddInfoListRes result = memberService.getStoreAddress();
        return ResponseEntity.ok(BaseResponse.of(result));
    }
}
