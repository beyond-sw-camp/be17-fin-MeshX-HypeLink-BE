package MeshX.HypeLink.direct_store.as.controller;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.direct_store.as.model.dto.AsCreateReq;
import MeshX.HypeLink.direct_store.as.model.dto.AsUpdateReq;
import MeshX.HypeLink.direct_store.as.model.dto.MyAsDetailRes;
import MeshX.HypeLink.direct_store.as.model.dto.MyAsListRes;
import MeshX.HypeLink.direct_store.as.service.DirectStoreAsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store/as")
@RequiredArgsConstructor
public class DirectStoreAsController {
    private final DirectStoreAsService directStoreAsService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> create(
            @RequestBody AsCreateReq dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        Member member = directStoreAsService.getMemberByUserName(userDetails.getUsername());
        directStoreAsService.create(member,dto);
        return ResponseEntity.ok(BaseResponse.of("AS 신청이 완료되었습니다."));
    }

    @GetMapping("/list")
    public ResponseEntity<BaseResponse<List<MyAsListRes>>> list(@AuthenticationPrincipal UserDetails userDetails) {
        Member member = directStoreAsService.getMemberByUserName(userDetails.getUsername());
        List<MyAsListRes> result = directStoreAsService.getMyAsList(member);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<MyAsDetailRes>> read(
            @PathVariable Integer id,
            @AuthenticationPrincipal UserDetails userDetails) {
        Member member = directStoreAsService.getMemberByUserName(userDetails.getUsername());
        MyAsDetailRes response = directStoreAsService.getMyAsDetail(id, member);
        return ResponseEntity.ok(BaseResponse.of(response));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<MyAsDetailRes>> update(
            @PathVariable Integer id,
            @RequestBody AsUpdateReq dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        Member member = directStoreAsService.getMemberByUserName(userDetails.getUsername());
        MyAsDetailRes response = directStoreAsService.updateAsRequest(id, dto, member);
        return ResponseEntity.ok(BaseResponse.of(response));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> delete(
            @PathVariable Integer id,
            @AuthenticationPrincipal UserDetails userDetails) {
        Member member = directStoreAsService.getMemberByUserName(userDetails.getUsername());
        directStoreAsService.deleteAsRequest(id, member);
        return ResponseEntity.ok(BaseResponse.of("AS 요청이 삭제되었습니다."));
    }
}
