package com.example.apiauth.adapter.in.internal;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiauth.usecase.port.out.usecase.MemberQueryUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "내부 인증 API", description = "마이크로서비스 간 내부 통신용 인증 API")
@WebAdapter
@RequestMapping("/internal/auth")
@RequiredArgsConstructor
public class InternalAuthController {
    private final MemberQueryUseCase memberQueryUseCase;

    @Operation(summary = "이메일로 회원 ID 조회", description = "이메일 주소를 통해 회원 ID를 조회합니다. (내부 API)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음", content = @Content)
    })
    @GetMapping("/member")
    public BaseResponse<Integer> getMemberIdByEmail(@Parameter(description = "회원 이메일") @RequestParam("email") String email) {
        Integer result = memberQueryUseCase.getMemberIdByEmail(email);
        return BaseResponse.of(result);
    }
}