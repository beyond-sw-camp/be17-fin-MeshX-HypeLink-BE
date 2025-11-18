package MeshX.HypeLink.direct_store.as.controller;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.direct_store.as.model.dto.AsCreateReq;
import MeshX.HypeLink.direct_store.as.model.dto.AsUpdateReq;
import MeshX.HypeLink.direct_store.as.model.dto.MyAsDetailRes;
import MeshX.HypeLink.direct_store.as.model.dto.MyAsListRes;
import MeshX.HypeLink.direct_store.as.service.DirectStoreAsService;
import com.example.apiclients.annotation.GetMemberEmail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "직영점 A/S 관리", description = "직영점 A/S 신청, 조회, 수정, 삭제 API")
@RestController
@RequestMapping("/api/store/as")
@RequiredArgsConstructor
public class DirectStoreAsController {
    private final DirectStoreAsService directStoreAsService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> create(
            @RequestBody AsCreateReq dto,
            @GetMemberEmail String email) {
        Member member = directStoreAsService.getMemberByUserName(email);
        directStoreAsService.create(member,dto);
        return ResponseEntity.ok(BaseResponse.of("AS 신청이 완료되었습니다."));
    }

    @GetMapping("/list")
    public ResponseEntity<BaseResponse<List<MyAsListRes>>> list(@GetMemberEmail String email) {
        Member member = directStoreAsService.getMemberByUserName(email);
        List<MyAsListRes> result = directStoreAsService.getMyAsList(member);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<MyAsDetailRes>> read(
            @PathVariable Integer id,
            @GetMemberEmail String email) {
        Member member = directStoreAsService.getMemberByUserName(email);
        MyAsDetailRes response = directStoreAsService.getMyAsDetail(id, member);
        return ResponseEntity.ok(BaseResponse.of(response));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<MyAsDetailRes>> update(
            @PathVariable Integer id,
            @RequestBody AsUpdateReq dto,
            @GetMemberEmail String email) {
        Member member = directStoreAsService.getMemberByUserName(email);
        MyAsDetailRes response = directStoreAsService.updateAsRequest(id, dto, member);
        return ResponseEntity.ok(BaseResponse.of(response));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> delete(
            @PathVariable Integer id,
            @GetMemberEmail String email) {
        Member member = directStoreAsService.getMemberByUserName(email);
        directStoreAsService.deleteAsRequest(id, member);
        return ResponseEntity.ok(BaseResponse.of("AS 요청이 삭제되었습니다."));
    }
}
