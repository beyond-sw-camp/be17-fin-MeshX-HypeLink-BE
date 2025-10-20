package MeshX.HypeLink.auth.controller;

import MeshX.HypeLink.auth.model.dto.req.DriverListReqDto;
import MeshX.HypeLink.auth.model.dto.req.StoreWithPosListReqDto;
import MeshX.HypeLink.auth.model.dto.res.UserListResDto;
import MeshX.HypeLink.auth.service.MemberService;
import MeshX.HypeLink.common.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@AllArgsConstructor
public class UserController {
    private final MemberService memberService;

    @GetMapping("/member/list")
    public ResponseEntity<BaseResponse<UserListResDto>> list(){
        UserListResDto result = memberService.list();

        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/driver/list")
    public ResponseEntity<BaseResponse<List<DriverListReqDto>>> driverList(){
        List<DriverListReqDto> result = memberService.dirverList();

        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/store/list")
    public ResponseEntity<BaseResponse<List<StoreWithPosListReqDto>>> storeList(){
        List<StoreWithPosListReqDto> result = memberService.storeList();

        return ResponseEntity.ok(BaseResponse.of(result));
    }
}