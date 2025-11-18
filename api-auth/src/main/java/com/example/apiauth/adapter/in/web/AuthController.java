package com.example.apiauth.adapter.in.web;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiauth.adapter.in.web.dto.LoginResDto;
import com.example.apiauth.adapter.in.web.dto.ReissueTokenResDto;
import com.example.apiauth.common.utils.CookieUtils;
import com.example.apiauth.constants.AuthSwaggerConstants;
import com.example.apiauth.domain.model.value.AuthTokens;
import com.example.apiauth.usecase.port.in.LoginCommand;
import com.example.apiauth.usecase.port.in.RegisterCommand;
import com.example.apiauth.usecase.port.in.ReissueTokenCommand;
import com.example.apiauth.usecase.port.out.usecase.AuthUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증 및 사용자 관리", description = "회원가입, 로그인, 토큰 재발급 등 인증 관련 API 및 사용자 정보 관리 API")
@WebAdapter
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${jwt.refresh-token-expiration-ms}")
    private long refreshTokenExpirationMs;

    private final AuthUseCase authUseCase;

    @Operation(summary = "로그인", description = "사용자 인증을 통해 액세스 토큰과 리프레시 토큰을 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.LOGIN_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패 (잘못된 이메일 또는 비밀번호)", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResDto>> login(@RequestBody LoginCommand dto) {
        LoginResDto result = authUseCase.login(dto);

        return createTokenResponse(result);
    }


    @Operation(summary = "토큰 재발급", description = "리프레시 토큰을 사용하여 새로운 액세스 토큰과 리프레시 토큰을 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReissueTokenResDto.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.TOKEN_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "유효하지 않거나 만료된 리프레시 토큰", content = @Content)
    })
    @PostMapping("/reissue")
    public ResponseEntity<ReissueTokenResDto> reissue(@CookieValue("refresh_token") String refreshToken) {
        ReissueTokenCommand reissueTokenCommand = new ReissueTokenCommand(refreshToken);
        AuthTokens result = authUseCase.reissue(reissueTokenCommand);

        return createTokenResponse(result);
    }

    @Operation(summary = "로그아웃", description = "현재 사용자의 세션을 종료하고 토큰을 무효화합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.LOGOUT_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "유효하지 않거나 만료된 액세스 토큰", content = @Content)
    })
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<String>> logout(@RequestHeader("Authorization") String authorizationHeader) {
        String accessToken = authorizationHeader.substring("Bearer ".length());
        authUseCase.logout(accessToken);

        return createTokenResponse();
    }

    @Operation(summary = "회원가입", description = "새로운 사용자를 시스템에 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.REGISTER_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않거나, 이미 존재하는 이메일입니다.", content = @Content)
    })
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
