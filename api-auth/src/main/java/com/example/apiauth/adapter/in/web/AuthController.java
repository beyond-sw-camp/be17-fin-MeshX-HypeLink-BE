package com.example.apiauth.adapter.in.web;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiauth.adapter.in.web.dto.LoginResDto;
import com.example.apiauth.common.utils.CookieUtils;
import com.example.apiauth.usecase.port.in.LoginCommand;
import com.example.apiauth.usecase.port.out.usecase.AuthUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequestMapping("/api/auth")
public class AuthController {
    private AuthUseCase authUseCase;

    @Value("${jwt.refresh-token-expiration-ms}")
    private long refreshTokenExpirationMs;


    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResDto>> login(@RequestBody LoginCommand dto) {
        LoginResDto result = authUseCase.login(dto);

        ResponseCookie cookie = CookieUtils.createRefreshTokenCookie(result.getAuthTokens().getRefreshToken(), refreshTokenExpirationMs);

        return ResponseEntity.ok(BaseResponse.of(result));
    }
}
