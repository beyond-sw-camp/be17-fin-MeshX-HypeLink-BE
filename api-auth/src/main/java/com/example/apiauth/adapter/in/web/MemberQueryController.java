package com.example.apiauth.adapter.in.web;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiauth.adapter.in.web.dto.*;
import com.example.apiauth.constants.AuthSwaggerConstants;
import com.example.apiauth.usecase.port.out.usecase.MemberQueryUseCase;
import com.example.apiclients.annotation.GetMemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import com.example.apiclients.annotation.RequireRole;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "회원 조회", description = "회원, 매장, 드라이버, POS 정보 조회 API")
@WebAdapter
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberQueryController {

    private final MemberQueryUseCase memberQueryUseCase;

    // member중 POS 제외
    @Operation(summary = "회원 목록 조회 (POS 제외)", description = "POS를 제외한 모든 회원 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.USER_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/member/list")
    public ResponseEntity<BaseResponse<UserListResDto>> list() {
        UserListResDto result = memberQueryUseCase.memberlistNotPos();
        return ResponseEntity.ok(BaseResponse.of(result));
    }


    @Operation(summary = "드라이버 목록 조회", description = "모든 드라이버의 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.DRIVER_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/driver/list")
    public ResponseEntity<BaseResponse<List<DriverListResDto>>> driverList() {
        List<DriverListResDto> result = memberQueryUseCase.dirverList();
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "매장 및 POS 목록 조회", description = "모든 매장과 해당 매장의 POS 목록을 함께 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.STORE_WITH_POS_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/storepos/list")
    public ResponseEntity<BaseResponse<List<StoreWithPosResDto>>> storeWithPosList() {
        List<StoreWithPosResDto> result = memberQueryUseCase.storeWithPosList();

        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "매장 목록 조회 (페이징)", description = "페이징 및 검색 조건을 적용하여 매장 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.STORE_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/store/list")
    public ResponseEntity<BaseResponse<Page<StoreListResDto>>> storeList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @Parameter(description = "검색 키워드") @RequestParam(defaultValue = "") String keyWord,
            @Parameter(description = "매장 상태 (all/OPEN/CLOSED)") @RequestParam(defaultValue = "all") String status) {
        Page<StoreListResDto> result = memberQueryUseCase.storeList(pageable, keyWord, status);

        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "내 매장 조회", description = "현재 로그인한 사용자의 매장 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.STORE_WITH_POS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/mystore/read")
    public ResponseEntity<BaseResponse<StoreWithPosResDto>> readMyStore(@GetMemberId Integer memberId) {
        StoreWithPosResDto result = memberQueryUseCase.readMyStore(memberId);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "다른 매장 조회", description = "특정 ID의 매장 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.STORE_WITH_POS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음", content = @Content)
    })
    @GetMapping("/otherstore/read/{id}")
    public ResponseEntity<BaseResponse<StoreWithPosResDto>> readOtherStore(@Parameter(description = "매장 ID") @PathVariable Integer id) {
        StoreWithPosResDto result = memberQueryUseCase.readOtherStroe(id);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "매장 상세 정보 조회", description = "특정 ID의 매장 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.STORE_INFO_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음", content = @Content)
    })
    @GetMapping("/storeinfo/read/{id}")
    public ResponseEntity<BaseResponse<StoreInfoResDto>> readStoreInfo(@Parameter(description = "매장 ID") @PathVariable Integer id) {
        StoreInfoResDto result = memberQueryUseCase.readStoreInfo(id);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "사용자 정보 조회", description = "특정 ID의 사용자 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.USER_READ_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content)
    })
    @GetMapping("/user/read/{id}")
    public ResponseEntity<BaseResponse<UserReadResDto>> readUser(@Parameter(description = "사용자 ID") @PathVariable Integer id) {
        UserReadResDto result = memberQueryUseCase.userRead(id);

        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "매장 주소 목록 조회", description = "모든 매장의 주소 정보를 조회합니다. (ADMIN, MANAGER 권한 필요)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.STORE_ADD_INFO_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content)
    })
    @RequireRole({"ADMIN", "MANAGER"})
    @GetMapping("/store/list/address")
    public ResponseEntity<BaseResponse<StoreAddInfoListRes>> getStoreAddress() {
        StoreAddInfoListRes result = memberQueryUseCase.getStoreAddress();
        return ResponseEntity.ok(BaseResponse.of(result));
    }

}
