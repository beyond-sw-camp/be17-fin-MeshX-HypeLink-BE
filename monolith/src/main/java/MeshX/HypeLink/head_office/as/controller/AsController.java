package MeshX.HypeLink.head_office.as.controller;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.as.model.dto.req.AsStatusUpdateReq;
import MeshX.HypeLink.head_office.as.model.dto.req.CommentCreateReq;
import MeshX.HypeLink.head_office.as.model.dto.res.AsDetailRes;
import MeshX.HypeLink.head_office.as.model.dto.res.AsListPagingRes;
import MeshX.HypeLink.head_office.as.model.dto.res.AsListRes;
import MeshX.HypeLink.head_office.as.service.AsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/as")
@RequiredArgsConstructor
public class AsController {
    private final AsService asService;

    @GetMapping("/list")
    public ResponseEntity<BaseResponse<List<AsListRes>>> list() {
        List<AsListRes> response = asService.getAllAsRequests();
        return ResponseEntity.ok(BaseResponse.of(response));
    }

    @GetMapping("/list/paging")
    public ResponseEntity<BaseResponse<AsListPagingRes>> listPaging(
            Pageable pageable,
            @RequestParam String keyWord,
            @RequestParam String status) {
        AsListPagingRes response = asService.getAllAsRequests(pageable, keyWord, status);
        return ResponseEntity.status(200).body(BaseResponse.of(response));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<AsDetailRes>> read(@PathVariable Integer id) {
        AsDetailRes response = asService.getAsDetail(id);
        return ResponseEntity.ok(BaseResponse.of(response));
    }

    @PatchMapping("/update/status/{id}")
    public ResponseEntity<BaseResponse<AsDetailRes>> updateStatus(
            @PathVariable Integer id,
            @RequestBody AsStatusUpdateReq req,
            @AuthenticationPrincipal UserDetails userDetails) {
        asService.getMemberByUserNameAndValidate(userDetails.getUsername());
        AsDetailRes response = asService.updateAsStatus(id, req);
        return ResponseEntity.ok(BaseResponse.of(response));
    }

    @PostMapping("/create/comment/{id}")
    public ResponseEntity<BaseResponse<AsDetailRes>> createComment(
            @PathVariable Integer id,
            @RequestBody CommentCreateReq req,
            @AuthenticationPrincipal UserDetails userDetails) {
        Member member = asService.getMemberByUserNameAndValidate(userDetails.getUsername());
        AsDetailRes response = asService.createComment(id, req, member);
        return ResponseEntity.ok(BaseResponse.of(response));
    }
}
