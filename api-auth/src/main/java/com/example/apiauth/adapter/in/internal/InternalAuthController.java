package com.example.apiauth.adapter.in.internal;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiauth.usecase.port.out.persistence.MemberQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@WebAdapter
@RequestMapping("/internal/auth")
@RequiredArgsConstructor
public class InternalAuthController {
    private final MemberQueryUseCase memberQueryUseCase;

    @GetMapping("/member")
    public BaseResponse<Integer> getMemberIdByEmail(@RequestParam("email") String email) {
        Integer memberId = memberQueryUseCase.getMemberIdByEmail(email);
        return BaseResponse.of(memberId);
    }
}
