package MeshX.HypeLink.direct_store.as.controller;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.direct_store.as.constansts.DirectStoreSwaggerConstants;
import MeshX.HypeLink.direct_store.as.model.dto.AsCreateReq;
import MeshX.HypeLink.direct_store.as.model.dto.AsUpdateReq;
import MeshX.HypeLink.direct_store.as.model.dto.MyAsDetailRes;
import MeshX.HypeLink.direct_store.as.model.dto.MyAsListRes;
import MeshX.HypeLink.direct_store.as.service.DirectStoreAsService;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "직영점 AS 관리", description = "직영점의 AS 신청 및 관리 관련 API")
@RestController
@RequestMapping("/api/store/as")
@RequiredArgsConstructor
public class DirectStoreAsController {
    private final DirectStoreAsService directStoreAsService;

    @Operation(summary = "AS 신청", description = "새로운 AS를 신청합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AS 신청 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.AS_CREATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "AS 신청 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AsCreateReq.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.AS_CREATE_REQ_EXAMPLE)
                    )
            )
            @RequestBody AsCreateReq dto,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        Member member = directStoreAsService.getMemberByUserName(userDetails.getUsername());
        directStoreAsService.create(member,dto);
        return ResponseEntity.ok(BaseResponse.of("AS 신청이 완료되었습니다."));
    }

    @Operation(summary = "내가 신청한 AS 목록 조회", description = "현재 로그인된 사용자가 신청한 AS 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AS 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.MY_AS_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<List<MyAsListRes>>> list(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        Member member = directStoreAsService.getMemberByUserName(userDetails.getUsername());
        List<MyAsListRes> result = directStoreAsService.getMyAsList(member);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "내가 신청한 AS 상세 조회", description = "내가 신청한 특정 AS의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AS 상세 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.MY_AS_DETAIL_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 AS를 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<MyAsDetailRes>> read(
            @Parameter(description = "조회할 AS의 ID") @PathVariable Integer id,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        Member member = directStoreAsService.getMemberByUserName(userDetails.getUsername());
        MyAsDetailRes response = directStoreAsService.getMyAsDetail(id, member);
        return ResponseEntity.ok(BaseResponse.of(response));
    }

    @Operation(summary = "AS 정보 수정", description = "내가 신청한 AS의 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AS 정보 수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.AS_UPDATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 AS를 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<MyAsDetailRes>> update(
            @Parameter(description = "수정할 AS의 ID") @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "AS 수정 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AsUpdateReq.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.AS_UPDATE_REQ_EXAMPLE)
                    )
            )
            @RequestBody AsUpdateReq dto,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        Member member = directStoreAsService.getMemberByUserName(userDetails.getUsername());
        MyAsDetailRes response = directStoreAsService.updateAsRequest(id, dto, member);
        return ResponseEntity.ok(BaseResponse.of(response));
    }

    @Operation(summary = "AS 요청 삭제", description = "내가 신청한 AS 요청을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AS 요청 삭제 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectStoreSwaggerConstants.AS_DELETE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 AS를 찾을 수 없습니다.", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> delete(
            @Parameter(description = "삭제할 AS의 ID") @PathVariable Integer id,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        Member member = directStoreAsService.getMemberByUserName(userDetails.getUsername());
        directStoreAsService.deleteAsRequest(id, member);
        return ResponseEntity.ok(BaseResponse.of("AS 요청이 삭제되었습니다."));
    }
}

