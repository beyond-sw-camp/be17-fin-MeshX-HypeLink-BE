package MeshX.HypeLink.auth.controller;

import MeshX.HypeLink.auth.model.dto.UserListResDto;
import MeshX.HypeLink.auth.service.AuthService;
import MeshX.HypeLink.auth.service.MemberService;
import MeshX.HypeLink.common.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@AllArgsConstructor
public class UserController {
    private final MemberService memberService;

    @GetMapping("/list")
    public ResponseEntity<BaseResponse<UserListResDto>> list(){
        UserListResDto result = memberService.list();

        return ResponseEntity.ok(BaseResponse.of(result));
    }

}
