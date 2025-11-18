package MeshX.HypeLink.head_office.as.controller;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.as.model.dto.req.AsStatusUpdateReq;
import MeshX.HypeLink.head_office.as.model.dto.req.CommentCreateReq;
import MeshX.HypeLink.head_office.as.model.dto.res.AsDetailRes;
import MeshX.HypeLink.head_office.as.model.dto.res.AsListPagingRes;
import MeshX.HypeLink.head_office.as.model.dto.res.AsListRes;
import MeshX.HypeLink.head_office.as.service.AsService;
import com.example.apiclients.annotation.GetMemberEmail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "본사 A/S 관리", description = "본사 A/S 요청 조회, 상태 변경, 댓글 작성 API")
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
            @GetMemberEmail String email) {
        asService.getMemberByUserNameAndValidate(email);
        AsDetailRes response = asService.updateAsStatus(id, req);
        return ResponseEntity.ok(BaseResponse.of(response));
    }

    @PostMapping("/create/comment/{id}")
    public ResponseEntity<BaseResponse<AsDetailRes>> createComment(
            @PathVariable Integer id,
            @RequestBody CommentCreateReq req,
            @GetMemberEmail String email) {
        Member member = asService.getMemberByUserNameAndValidate(email);
        AsDetailRes response = asService.createComment(id, req, member);
        return ResponseEntity.ok(BaseResponse.of(response));
    }
}
