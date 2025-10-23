package MeshX.HypeLink.auth.controller;

import MeshX.HypeLink.auth.model.dto.req.DriverListReqDto;
import MeshX.HypeLink.auth.model.dto.req.StoreStateReqDto;
import MeshX.HypeLink.auth.model.dto.res.*;
import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.service.MemberService;
import MeshX.HypeLink.common.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/member")
@AllArgsConstructor
public class UserController {
    private final MemberService memberService;

    @GetMapping("/member/list")
    public ResponseEntity<BaseResponse<UserListResDto>> list() {
        UserListResDto result = memberService.list();
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/driver/list")
    public ResponseEntity<BaseResponse<List<DriverListReqDto>>> driverList() {
        List<DriverListReqDto> result = memberService.dirverList();

        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/storepos/list")
    public ResponseEntity<BaseResponse<List<StoreWithPosResDto>>> storeWithPosList() {
        List<StoreWithPosResDto> result = memberService.storeWithPosList();

        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/store/list")
    public ResponseEntity<BaseResponse<List<StoreListResDto>>> storeList() {
        List<StoreListResDto> result = memberService.storeList();

        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/messageuser/list")
    public ResponseEntity<BaseResponse<List<MessageUserListResDto>>> messageUserList() {
        List<MessageUserListResDto> result = memberService.messageUserList();

        return ResponseEntity.ok(BaseResponse.of(result));
    }


    @GetMapping("/mystore/read")
    public ResponseEntity<BaseResponse<StoreWithPosResDto>> readMyStore(@AuthenticationPrincipal UserDetails userDetails) {
        Member member = memberService.findMember( userDetails.getUsername());
        StoreWithPosResDto result = memberService.readMyStore(member);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/otherstore/read/{id}")
    public ResponseEntity<BaseResponse<StoreWithPosResDto>> readOtherStore(@PathVariable Integer id) {
        StoreWithPosResDto result = memberService.readOtherStroe(id);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/storeinfo/read/{id}")
    public ResponseEntity<BaseResponse<StoreInfoResDto>> readStoreInfo(@PathVariable Integer id) {
        StoreInfoResDto result = memberService.readStoreInfo(id);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @PatchMapping("/store/{id}")
    public ResponseEntity<BaseResponse<String>> updateStore(@PathVariable Integer id, @RequestBody StoreInfoResDto dto) {
        memberService.updateStoreInfo(id, dto);
        return ResponseEntity.ok(BaseResponse.of("매장 정보가 성공적으로 수정되었습니다."));
    }

    @PatchMapping("/store/state/{id}")
    public ResponseEntity<BaseResponse<String>> readStoreState(@PathVariable Integer id, @RequestBody StoreStateReqDto dto) {
        memberService.storeStateUpdate(id,dto);

        return ResponseEntity.ok(BaseResponse.of("성공적으로 변경하였습니다."));
    }

    @GetMapping("/user/read/{id}")
    public ResponseEntity<BaseResponse<UserReadResDto>> readUser(@PathVariable Integer id) {
        UserReadResDto result = memberService.userRead(id);

        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PatchMapping("/user/update/{id}")
    public ResponseEntity<BaseResponse<String>> updateUser(@PathVariable Integer id,@RequestBody UserReadResDto dto) {
        memberService.updateUser(id,dto);

        return ResponseEntity.ok(BaseResponse.of("성공적으로 변경하였습니다."));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteMember(@PathVariable Integer id) {
        memberService.deleteUser(id);

        return ResponseEntity.ok(BaseResponse.of("사용자가 성공적으로 삭제되었습니다."));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/store/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteStore(@PathVariable Integer id) {
        memberService.deleteStore(id);

        return ResponseEntity.ok(BaseResponse.of("사용자가 성공적으로 삭제되었습니다."));
    }

    @DeleteMapping("/pos/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deletePos(@PathVariable Integer id) {
        memberService.deletePos(id);
        return ResponseEntity.ok(BaseResponse.of("사용자가 성공적으로 삭제되었습니다."));
    }

    @DeleteMapping("/driver/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteDriver(@PathVariable Integer id) {
        memberService.deleteDriver(id);
        return ResponseEntity.ok(BaseResponse.of("사용자가 성공적으로 삭제되었습니다."));
    }

}