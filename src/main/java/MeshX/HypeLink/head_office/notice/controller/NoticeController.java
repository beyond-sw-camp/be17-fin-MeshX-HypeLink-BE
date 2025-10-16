package MeshX.HypeLink.head_office.notice.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.notice.constansts.SwaggerConstants;
import MeshX.HypeLink.head_office.notice.model.dto.request.NoticeCreateReq;
import MeshX.HypeLink.head_office.notice.model.dto.request.NoticeUpdateReq;
import MeshX.HypeLink.head_office.notice.model.dto.response.NoticeInfoListRes;
import MeshX.HypeLink.head_office.notice.model.dto.response.NoticeInfoRes;
import MeshX.HypeLink.head_office.notice.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "본사 공지사항 관리", description = "전체 시스템 사용자 또는 특정 그룹을 대상으로 하는 공지사항을 관리하는 API")
@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @Operation(summary = "공지사항 작성", description = "새로운 공지사항을 작성하여 시스템에 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "공지사항 작성 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createNotice(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "작성할 공지사항의 제목과 내용",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NoticeCreateReq.class),
                            examples = @ExampleObject(value = SwaggerConstants.NOTICE_CREATE_REQUEST)
                    )
            )
            @RequestBody NoticeCreateReq dto) {
        noticeService.createNotice(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.of("생성되었습니다."));
    }

    @Operation(summary = "전체 공지사항 목록 조회", description = "시스템에 등록된 모든 공지사항 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.NOTICE_INFO_LIST_RESPONSE)))
    })
    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<NoticeInfoListRes>> readNotices() {
        NoticeInfoListRes noticeInfoListRes = noticeService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(noticeInfoListRes));
    }

    @Operation(summary = "공지사항 상세 조회", description = "공지사항 ID를 사용하여 특정 공지사항의 상세 내용을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 상세 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.NOTICE_INFO_RESPONSE))),
            @ApiResponse(responseCode = "404", description = "해당 ID의 공지사항을 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<NoticeInfoRes>> readNotice(@Parameter(description = "조회할 공지사항의 ID") @PathVariable Integer id) {
        NoticeInfoRes noticeInfoRes = noticeService.readDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(noticeInfoRes));
    }

    @Operation(summary = "공지사항 수정", description = "기존 공지사항의 제목, 내용, 공개 여부 등을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.NOTICE_INFO_RESPONSE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 ID의 공지사항을 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<NoticeInfoRes>> updateNotice(
            @Parameter(description = "수정할 공지사항의 ID") @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "수정할 공지사항의 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NoticeUpdateReq.class),
                            examples = @ExampleObject(value = SwaggerConstants.NOTICE_UPDATE_REQUEST)
                    )
            )
            @RequestBody NoticeUpdateReq dto) {
        NoticeInfoRes noticeInfoRes = noticeService.update(id, dto.getTitle(), dto.getContents(), dto.getIsOpen());
        return ResponseEntity.status(200).body(BaseResponse.of(noticeInfoRes));
    }

    @Operation(summary = "공지사항 삭제", description = "공지사항 ID를 사용하여 특정 공지사항을 시스템에서 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "해당 ID의 공지사항을 찾을 수 없습니다.", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteNotice(@Parameter(description = "삭제할 공지사항의 ID") @PathVariable Integer id) {
        noticeService.delete(id);
        return ResponseEntity.status(200).body(BaseResponse.of("성공적으로 삭제 되었습니다."));
    }
}
