package MeshX.HypeLink.auth.controller;

import MeshX.HypeLink.auth.model.dto.req.DriverListReqDto;
import MeshX.HypeLink.auth.model.dto.res.MessageUserListResDto;
import MeshX.HypeLink.auth.model.dto.res.StoreListResDto;
import MeshX.HypeLink.auth.model.dto.res.StoreWithPosResDto;
import MeshX.HypeLink.auth.model.dto.res.UserListResDto;
import MeshX.HypeLink.auth.service.MemberService;
import MeshX.HypeLink.common.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<BaseResponse<Integer>> readStoreList(@AuthenticationPrincipal Integer id) {
        log.warn(id.toString());
        return ResponseEntity.ok(BaseResponse.of(123));
    }

}