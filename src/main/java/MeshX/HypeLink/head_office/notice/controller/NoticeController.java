package MeshX.HypeLink.head_office.notice.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.notice.model.dto.request.NoticeCreateReq;
import MeshX.HypeLink.head_office.notice.model.dto.request.NoticeUpdateReq;
import MeshX.HypeLink.head_office.notice.model.dto.response.NoticeInfoListRes;
import MeshX.HypeLink.head_office.notice.model.dto.response.NoticeDetailRes;
import MeshX.HypeLink.head_office.notice.model.dto.response.NoticeListResponse;
import MeshX.HypeLink.head_office.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createNotice(@RequestBody NoticeCreateReq dto) {
        noticeService.createNotice(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("생성되었습니다."));
    }

    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<NoticeInfoListRes>> readNotices() {
        NoticeInfoListRes noticeInfoListRes = noticeService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(noticeInfoListRes));
    }

    @GetMapping("/read/page/all")
    public ResponseEntity<BaseResponse<PageRes<NoticeListResponse>>> readNotices(PageReq pageReq) {
        PageRes<NoticeListResponse> pageRes = noticeService.readList(pageReq);
        return ResponseEntity.status(200).body(BaseResponse.of(pageRes));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<NoticeDetailRes>> readNotice(@PathVariable Integer id) {
        NoticeDetailRes noticeDetailRes = noticeService.readDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(noticeDetailRes));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<NoticeDetailRes>> updateNotice(@PathVariable Integer id,
                                                                      @RequestBody NoticeUpdateReq dto) {
        NoticeDetailRes noticeDetailRes = noticeService.update(id, dto);
        return ResponseEntity.status(200).body(BaseResponse.of(noticeDetailRes));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteNotice(@PathVariable Integer id) {
        noticeService.delete(id);
        return ResponseEntity.status(200).body(BaseResponse.of("성공적으로 삭제 되었습니다."));
    }
}