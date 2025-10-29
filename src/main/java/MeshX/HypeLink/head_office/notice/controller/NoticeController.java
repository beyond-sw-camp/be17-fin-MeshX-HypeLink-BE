package MeshX.HypeLink.head_office.notice.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.notice.constants.NoticeSwaggerConstants;
import MeshX.HypeLink.head_office.notice.model.dto.request.NoticeCreateReq;
import MeshX.HypeLink.head_office.notice.model.dto.request.NoticeUpdateReq;
import MeshX.HypeLink.head_office.notice.model.dto.response.NoticeInfoListRes;
import MeshX.HypeLink.head_office.notice.model.dto.response.NoticeDetailRes;
import MeshX.HypeLink.head_office.notice.model.dto.response.NoticeListResponse;
import MeshX.HypeLink.head_office.notice.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "본부 공지사항 관리", description = "본부에서 공지사항을 관리하는 API")
@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @Operation(summary = "공지사항 생성", description = "새로운 공지사항을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "생성 성공")
    })
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createNotice(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = @ExampleObject(value = NoticeSwaggerConstants.NOTICE_CREATE_REQ_EXAMPLE)))
            @RequestBody NoticeCreateReq dto) {
        noticeService.createNotice(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("생성되었습니다."));
    }

    @Operation(summary = "공지사항 전체 목록 조회", description = "모든 공지사항을 조회합니다.")
    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<NoticeInfoListRes>> readNotices() {
        NoticeInfoListRes noticeInfoListRes = noticeService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(noticeInfoListRes));
    }

    @Operation(summary = "공지사항 목록 조회 (페이징)", description = "공지사항 목록을 페이징하여 조회합니다.")
    @GetMapping("/read/page/all")
    public ResponseEntity<BaseResponse<PageRes<NoticeListResponse>>> readNotices(Pageable pageable) {
        PageRes<NoticeListResponse> pageRes = noticeService.readList(pageable);
        return ResponseEntity.status(200).body(BaseResponse.of(pageRes));
    }

    @Operation(summary = "공지사항 상세 조회", description = "특정 공지사항을 상세 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(examples = @ExampleObject(value = NoticeSwaggerConstants.NOTICE_DETAIL_RES_EXAMPLE)))
    })
    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<NoticeDetailRes>> readNotice(
            @Parameter(description = "공지사항 ID") @PathVariable Integer id) {
        NoticeDetailRes noticeDetailRes = noticeService.readDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(noticeDetailRes));
    }

    @Operation(summary = "공지사항 수정", description = "공지사항을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(examples = @ExampleObject(value = NoticeSwaggerConstants.NOTICE_DETAIL_RES_EXAMPLE)))
    })
    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<NoticeDetailRes>> updateNotice(
            @Parameter(description = "공지사항 ID") @PathVariable Integer id,
            @RequestBody NoticeUpdateReq dto) {
        NoticeDetailRes noticeDetailRes = noticeService.update(id, dto);
        return ResponseEntity.status(200).body(BaseResponse.of(noticeDetailRes));
    }

    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제합니다.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteNotice(
            @Parameter(description = "공지사항 ID") @PathVariable Integer id) {
        noticeService.delete(id);
        return ResponseEntity.status(200).body(BaseResponse.of("성공적으로 삭제 되었습니다."));
    }
}