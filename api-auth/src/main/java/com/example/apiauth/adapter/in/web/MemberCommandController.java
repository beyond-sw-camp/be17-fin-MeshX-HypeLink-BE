package com.example.apiauth.adapter.in.web;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiauth.adapter.in.web.dto.StoreInfoResDto;
import com.example.apiauth.adapter.in.web.dto.StoreStateReqDto;
import com.example.apiauth.adapter.in.web.dto.UserReadResDto;
import com.example.apiauth.constants.AuthSwaggerConstants;
import com.example.apiauth.usecase.port.out.usecase.MemberCommandUseCase;
import com.example.apiclients.annotation.RequireRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "회원 관리", description = "회원, 매장, POS, 드라이버 정보 수정 및 삭제 API")
@WebAdapter
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberCommandController {

    private final MemberCommandUseCase memberCommandUseCase;

    @Operation(summary = "매장 정보 수정", description = "특정 매장의 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.UPDATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음", content = @Content)
    })
    @PatchMapping("/store/{id}")
    public ResponseEntity<BaseResponse<String>> updateStore(
            @Parameter(description = "매장 ID") @PathVariable Integer id,
            @org.springframework.web.bind.annotation.RequestBody StoreInfoResDto dto) {
        memberCommandUseCase.updateStoreInfo(id, dto);
        return ResponseEntity.ok(BaseResponse.of("매장 정보가 성공적으로 수정되었습니다."));
    }

    @Operation(summary = "매장 상태 변경", description = "특정 매장의 영업 상태를 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "변경 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.UPDATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음", content = @Content)
    })
    @PatchMapping("/store/state/{id}")
    public ResponseEntity<BaseResponse<String>> readStoreState(
            @Parameter(description = "매장 ID") @PathVariable Integer id,
            @org.springframework.web.bind.annotation.RequestBody StoreStateReqDto dto) {
        memberCommandUseCase.storeStateUpdate(id, dto);

        return ResponseEntity.ok(BaseResponse.of("성공적으로 변경하였습니다."));
    }

    @Operation(summary = "사용자 정보 수정", description = "특정 사용자의 정보를 수정합니다. (ADMIN, MANAGER 권한 필요)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.UPDATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content)
    })
    @RequireRole({"ADMIN", "MANAGER"})
    @PatchMapping("/user/update/{id}")
    public ResponseEntity<BaseResponse<String>> updateUser(
            @Parameter(description = "사용자 ID") @PathVariable Integer id,
            @org.springframework.web.bind.annotation.RequestBody UserReadResDto dto) {
        memberCommandUseCase.updateUser(id, dto);

        return ResponseEntity.ok(BaseResponse.of("성공적으로 변경하였습니다."));
    }

    @Operation(summary = "사용자 삭제", description = "특정 사용자를 삭제합니다. (ADMIN 권한 필요)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.DELETE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content)
    })
    @RequireRole({"ADMIN"})
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteMember(@Parameter(description = "사용자 ID") @PathVariable Integer id) {
        memberCommandUseCase.deleteUser(id);

        return ResponseEntity.ok(BaseResponse.of("사용자가 성공적으로 삭제되었습니다."));
    }

    @Operation(summary = "매장 삭제", description = "특정 매장을 삭제합니다. (ADMIN, MANAGER 권한 필요)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.DELETE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content),
            @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음", content = @Content)
    })
    @RequireRole({"ADMIN", "MANAGER"})
    @DeleteMapping("/store/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteStore(@Parameter(description = "매장 ID") @PathVariable Integer id) {
        memberCommandUseCase.deleteStore(id);

        return ResponseEntity.ok(BaseResponse.of("사용자가 성공적으로 삭제되었습니다."));
    }

    @Operation(summary = "POS 삭제", description = "특정 POS를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.DELETE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "POS를 찾을 수 없음", content = @Content)
    })
    @DeleteMapping("/pos/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deletePos(@Parameter(description = "POS ID") @PathVariable Integer id) {
        memberCommandUseCase.deletePos(id);
        return ResponseEntity.ok(BaseResponse.of("사용자가 성공적으로 삭제되었습니다."));
    }

    @Operation(summary = "드라이버 삭제", description = "특정 드라이버를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.DELETE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "드라이버를 찾을 수 없음", content = @Content)
    })
    @DeleteMapping("/driver/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteDriver(@Parameter(description = "드라이버 ID") @PathVariable Integer id) {
        memberCommandUseCase.deleteDriver(id);
        return ResponseEntity.ok(BaseResponse.of("사용자가 성공적으로 삭제되었습니다."));
    }

}
