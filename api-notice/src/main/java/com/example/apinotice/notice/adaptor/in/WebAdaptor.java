package com.example.apinotice.notice.adaptor.in;

import MeshX.common.BaseResponse;
import MeshX.common.Page.PageRes;
import MeshX.common.WebAdapter;
import com.example.apinotice.constants.NoticeSwaggerConstants;
import com.example.apinotice.notice.usecase.port.in.WebPort;
import com.example.apinotice.notice.usecase.port.in.request.NoticeSaveCommand;
import com.example.apinotice.notice.usecase.port.in.request.NoticeUpdateCommand;
import com.example.apinotice.notice.usecase.port.out.response.NoticeInfoDto;
import com.example.apinotice.notice.usecase.port.out.response.NoticeListInfoDto;
import com.example.apinotice.notice.usecase.port.out.response.NoticePageListInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "공지사항 관리", description = "공지사항 등록, 조회, 수정 등 공지사항 관리 API")
@WebAdapter
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class WebAdaptor {
    private final WebPort webPort;

    @Operation(summary = "공지사항 등록", description = "새로운 공지사항을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 등록 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = NoticeSwaggerConstants.NOTICE_CREATE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createNotice(@RequestBody NoticeSaveCommand noticeSaveCommand) {
        webPort.create(noticeSaveCommand);
        return ResponseEntity.status(200).body(BaseResponse.of("생성되었습니다."));

    }

    @Operation(summary = "공지사항 상세 조회", description = "특정 ID의 공지사항 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = NoticeSwaggerConstants.NOTICE_READ_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "공지사항을 찾을 수 없음", content = @Content)
    })
    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<NoticeInfoDto>> readNotice(@Parameter(description = "공지사항 ID") @PathVariable Integer id) {
         NoticeInfoDto result = webPort.read(id);
         return ResponseEntity.status(200).body(BaseResponse.of(result));

    }

    @Operation(summary = "공지사항 목록 조회 (페이징)", description = "모든 공지사항 목록을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = NoticeSwaggerConstants.NOTICE_PAGE_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/read/page/all")
    public ResponseEntity<BaseResponse<PageRes<NoticePageListInfoDto>>> readNotices(Pageable pageable) {
        PageRes<NoticePageListInfoDto> pageRes = webPort.readList(pageable);
        return ResponseEntity.status(200).body(BaseResponse.of(pageRes));
    }

    @Operation(summary = "공지사항 전체 목록 조회", description = "모든 공지사항 목록을 조회합니다. (페이징 없음)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 전체 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = NoticeSwaggerConstants.NOTICE_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<NoticeListInfoDto>> readNotices() {
        NoticeListInfoDto noticeInfoListDto = webPort.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(noticeInfoListDto));
    }

    @Operation(summary = "공지사항 수정", description = "특정 ID의 공지사항 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = NoticeSwaggerConstants.NOTICE_UPDATE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "공지사항을 찾을 수 없음", content = @Content)
    })
    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<NoticeInfoDto>> updateNotice(
            @Parameter(description = "공지사항 ID") @PathVariable Integer id,
            @RequestBody NoticeUpdateCommand updatecommand) {
        NoticeInfoDto noticeInfoDto = webPort.update(id, updatecommand);
        return ResponseEntity.status(200).body(BaseResponse.of(noticeInfoDto));
    }

}
