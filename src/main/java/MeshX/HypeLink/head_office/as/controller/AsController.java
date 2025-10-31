package MeshX.HypeLink.head_office.as.controller;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.as.constants.HeadOfficeAsSwaggerConstants;
import MeshX.HypeLink.head_office.as.model.dto.req.AsStatusUpdateReq;
import MeshX.HypeLink.head_office.as.model.dto.req.CommentCreateReq;
import MeshX.HypeLink.head_office.as.model.dto.res.AsDetailRes;
import MeshX.HypeLink.head_office.as.model.dto.res.AsListPagingRes;
import MeshX.HypeLink.head_office.as.model.dto.res.AsListRes;
import MeshX.HypeLink.head_office.as.service.AsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "본부 AS 관리", description = "본부에서 직영점의 AS 요청을 조회 및 관리하는 API")
@RestController
@RequestMapping("/api/as")
@RequiredArgsConstructor
public class AsController {
    private final AsService asService;

    @Operation(summary = "전체 AS 목록 조회", description = "본부에서 모든 직영점의 AS 요청 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AS 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = HeadOfficeAsSwaggerConstants.AS_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<List<AsListRes>>> list() {
        List<AsListRes> response = asService.getAllAsRequests();
        return ResponseEntity.ok(BaseResponse.of(response));
    }

    @Operation(summary = "전체 AS 목록 조회 (페이징)", description = "본부에서 모든 직영점의 AS 요청 목록을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AS 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = HeadOfficeAsSwaggerConstants.AS_LIST_PAGING_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/list/paging")
    public ResponseEntity<BaseResponse<AsListPagingRes>> listPaging(
            Pageable pageable,
            @RequestParam String keyWord,
            @RequestParam String status) {
        AsListPagingRes response = asService.getAllAsRequests(pageable, keyWord, status);
        return ResponseEntity.status(200).body(BaseResponse.of(response));
    }

    @Operation(summary = "AS 상세 조회", description = "특정 AS 요청의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AS 상세 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = HeadOfficeAsSwaggerConstants.AS_DETAIL_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 AS를 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<AsDetailRes>> read(
            @Parameter(description = "조회할 AS의 ID") @PathVariable Integer id) {
        AsDetailRes response = asService.getAsDetail(id);
        return ResponseEntity.ok(BaseResponse.of(response));
    }

    @Operation(summary = "AS 상태 변경", description = "AS 요청의 상태를 변경합니다. (PENDING, APPROVED, REJECTED, COMPLETED)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AS 상태 변경 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = HeadOfficeAsSwaggerConstants.AS_STATUS_UPDATE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 AS를 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/update/status/{id}")
    public ResponseEntity<BaseResponse<AsDetailRes>> updateStatus(
            @Parameter(description = "상태를 변경할 AS의 ID") @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "변경할 AS 상태 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AsStatusUpdateReq.class),
                            examples = @ExampleObject(value = HeadOfficeAsSwaggerConstants.AS_STATUS_UPDATE_REQ_EXAMPLE)
                    )
            )
            @RequestBody AsStatusUpdateReq req,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        asService.getMemberByUserNameAndValidate(userDetails.getUsername());
        AsDetailRes response = asService.updateAsStatus(id, req);
        return ResponseEntity.ok(BaseResponse.of(response));
    }

    @Operation(summary = "AS에 댓글 작성", description = "AS 요청에 댓글을 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 작성 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = HeadOfficeAsSwaggerConstants.COMMENT_CREATE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 AS를 찾을 수 없습니다.", content = @Content)
    })
    @PostMapping("/create/comment/{id}")
    public ResponseEntity<BaseResponse<AsDetailRes>> createComment(
            @Parameter(description = "댓글을 작성할 AS의 ID") @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "댓글 내용",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentCreateReq.class),
                            examples = @ExampleObject(value = HeadOfficeAsSwaggerConstants.COMMENT_CREATE_REQ_EXAMPLE)
                    )
            )
            @RequestBody CommentCreateReq req,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        Member member = asService.getMemberByUserNameAndValidate(userDetails.getUsername());
        AsDetailRes response = asService.createComment(id, req, member);
        return ResponseEntity.ok(BaseResponse.of(response));
    }
}
