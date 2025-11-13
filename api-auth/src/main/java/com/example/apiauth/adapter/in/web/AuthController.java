package com.example.apiauth.adapter.in.web;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiauth.adapter.in.web.dto.LoginResDto;
import com.example.apiauth.adapter.in.web.dto.ReissueTokenResDto;
import com.example.apiauth.common.utils.CookieUtils;
import com.example.apiauth.domain.model.value.AuthTokens;
import com.example.apiauth.usecase.port.in.LoginCommand;
import com.example.apiauth.usecase.port.in.RegisterCommand;
import com.example.apiauth.usecase.port.in.ReissueTokenCommand;
import com.example.apiauth.usecase.port.out.usecase.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${jwt.refresh-token-expiration-ms}")
    private long refreshTokenExpirationMs;

    private final AuthUseCase authUseCase;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResDto>> login(@RequestBody LoginCommand dto) {
        LoginResDto result = authUseCase.login(dto);

        return createTokenResponse(result);
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueTokenResDto> reissue(@CookieValue("refresh_token") String refreshToken) {
        ReissueTokenCommand reissueTokenCommand = new ReissueTokenCommand(refreshToken);
        AuthTokens result = authUseCase.reissue(reissueTokenCommand);

        return createTokenResponse(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<String>> logout(@RequestHeader("Authorization") String authorizationHeader) {
        String accessToken = authorizationHeader.substring("Bearer ".length());
        authUseCase.logout(accessToken);

        return createTokenResponse();
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<String>> register(@RequestBody RegisterCommand command) {
        authUseCase.register(command);

        return ResponseEntity.ok(BaseResponse.of("회원가입이 성공하였습니다."));
    }

    private ResponseEntity<ReissueTokenResDto> createTokenResponse(AuthTokens authTokens) {
        ResponseCookie cookie = CookieUtils.createRefreshTokenCookie(authTokens.getRefreshToken(), refreshTokenExpirationMs);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new ReissueTokenResDto(authTokens.getAccessToken()));
    }

    private ResponseEntity<BaseResponse<LoginResDto>> createTokenResponse(LoginResDto loginResDto) {
        ResponseCookie cookie = CookieUtils.createRefreshTokenCookie(loginResDto.getAuthTokens().getRefreshToken(), refreshTokenExpirationMs);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body((BaseResponse.of(loginResDto)));
    }

    private ResponseEntity<BaseResponse<String>> createTokenResponse() {
        ResponseCookie emptyCookie = CookieUtils.createEmptyCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, emptyCookie.toString())
                .body(BaseResponse.of("정상적으로 로그아웃 처리되었습니다."));
    }

}
