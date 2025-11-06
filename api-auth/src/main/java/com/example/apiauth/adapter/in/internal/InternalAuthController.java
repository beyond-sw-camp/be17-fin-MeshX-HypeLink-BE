package com.example.apiauth.adapter.in.internal;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiauth.usecase.port.out.usecase.MemberQueryUseCase;
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
        Integer result = memberQueryUseCase.getMemberIdByEmail(email);
        return BaseResponse.of(result);
    }
    @GetMapping("/store")
    public BaseResponse<Integer> getStoreIdByEmail(@RequestParam("email") String email) {
        Integer result = memberQueryUseCase.getStoreIdByEmail(email);
        return BaseResponse.of(result);
    }

    @GetMapping("/pos")
    public BaseResponse<Integer> getPosIdByEmail(@RequestParam("email") String email) {
        Integer result = memberQueryUseCase.getPosIdByEmail(email);
        return BaseResponse.of(result);
    }

    @GetMapping("/driver")
    public BaseResponse<Integer> getDriverIdByEmail(@RequestParam("email") String email) {
        Integer result = memberQueryUseCase.getDriverIdByEmail(email);
        return BaseResponse.of(result);
    }


}