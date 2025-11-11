package com.example.apiauth.adapter.in.web;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiauth.adapter.in.web.dto.StoreInfoResDto;
import com.example.apiauth.adapter.in.web.dto.StoreStateReqDto;
import com.example.apiauth.adapter.in.web.dto.UserReadResDto;
import com.example.apiauth.usecase.port.out.usecase.MemberCommandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@WebAdapter
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberCommandController {

    private final MemberCommandUseCase memberCommandUseCase;

    @PatchMapping("/store/{id}")
    public ResponseEntity<BaseResponse<String>> updateStore(
            @PathVariable Integer id,
            @RequestBody StoreInfoResDto dto) {
        memberCommandUseCase.updateStoreInfo(id, dto);
        return ResponseEntity.ok(BaseResponse.of("매장 정보가 성공적으로 수정되었습니다."));
    }

    @PatchMapping("/store/state/{id}")
    public ResponseEntity<BaseResponse<String>> readStoreState(
            @PathVariable Integer id,
            @RequestBody StoreStateReqDto dto) {
        memberCommandUseCase.storeStateUpdate(id, dto);

        return ResponseEntity.ok(BaseResponse.of("성공적으로 변경하였습니다."));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PatchMapping("/user/update/{id}")
    public ResponseEntity<BaseResponse<String>> updateUser(
            @PathVariable Integer id,
            @RequestBody UserReadResDto dto) {
        memberCommandUseCase.updateUser(id, dto);

        return ResponseEntity.ok(BaseResponse.of("성공적으로 변경하였습니다."));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteMember(@PathVariable Integer id) {
        memberCommandUseCase.deleteUser(id);

        return ResponseEntity.ok(BaseResponse.of("사용자가 성공적으로 삭제되었습니다."));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/store/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteStore(@PathVariable Integer id) {
        memberCommandUseCase.deleteStore(id);

        return ResponseEntity.ok(BaseResponse.of("사용자가 성공적으로 삭제되었습니다."));
    }

    @DeleteMapping("/pos/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deletePos(@PathVariable Integer id) {
        memberCommandUseCase.deletePos(id);
        return ResponseEntity.ok(BaseResponse.of("사용자가 성공적으로 삭제되었습니다."));
    }

    @DeleteMapping("/driver/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteDriver(@PathVariable Integer id) {
        memberCommandUseCase.deleteDriver(id);
        return ResponseEntity.ok(BaseResponse.of("사용자가 성공적으로 삭제되었습니다."));
    }

}
