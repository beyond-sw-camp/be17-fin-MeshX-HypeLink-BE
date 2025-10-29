package MeshX.HypeLink.auth.controller;

import MeshX.HypeLink.auth.constansts.AuthSwaggerConstants;
import MeshX.HypeLink.auth.model.dto.*;
import MeshX.HypeLink.auth.model.dto.req.LoginReqDto;
import MeshX.HypeLink.auth.model.dto.req.RegisterReqDto;
import MeshX.HypeLink.auth.model.dto.res.LoginResDto;
import MeshX.HypeLink.auth.model.dto.res.TokenResDto;
import MeshX.HypeLink.auth.service.AuthService;
import MeshX.HypeLink.auth.utils.CookieUtils;
import MeshX.HypeLink.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${jwt.refresh-token-expiration-ms}")
    private long refreshTokenExpirationMs;

    @Operation(summary = "회원가입", description = "새로운 사용자를 시스템에 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.REGISTER_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않거나, 이미 존재하는 이메일입니다.", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<BaseResponse<String>> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "회원가입 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterReqDto.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.REGISTER_REQ_EXAMPLE)
                    )
            )
            @RequestBody RegisterReqDto dto) {
        authService.register(dto);
        return ResponseEntity.ok(BaseResponse.of("회원가입이 성공하였습니다."));
    }

    @Operation(summary = "로그인", description = "사용자 인증을 통해 액세스 토큰과 리프레시 토큰을 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.LOGIN_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패 (잘못된 이메일 또는 비밀번호)", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResDto>> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "로그인 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginReqDto.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.LOGIN_REQ_EXAMPLE)
                    )
            )
            @RequestBody LoginReqDto dto) {
        LoginResDto result = authService.login(dto);

        ResponseCookie cookie = CookieUtils.createRefreshTokenCookie(result.getAuthTokens().getRefreshToken(), refreshTokenExpirationMs);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(BaseResponse.of(result, "로그인 성공"));
    }

    @Operation(summary = "토큰 재발급", description = "리프레시 토큰을 사용하여 새로운 액세스 토큰과 리프레시 토큰을 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResDto.class),
                            examples = @ExampleObject(value = AuthSwaggerConstants.TOKEN_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "유효하지 않거나 만료된 리프레시 토큰", content = @Content)
    })
    @PostMapping("/reissue")
    public ResponseEntity<TokenResDto> reissue(
            @Parameter(description = "리프레시 토큰 (쿠키)", required = true)
            @CookieValue("refresh_token") String refreshToken) {
        AuthTokens authTokens = authService.reissueTokens(refreshToken);
        return createTokenResponse(authTokens);
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
    public ResponseEntity<String> logout(
            @Parameter(description = "액세스 토큰 (Bearer)", required = true)
            @RequestHeader("Authorization") String authorizationHeader) {
        String accessToken = authorizationHeader.substring("Bearer ".length());
        authService.logout(accessToken);

        // 로그아웃 시 쿠키 삭제
        ResponseCookie emptyCookie = CookieUtils.createEmptyCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, emptyCookie.toString())
                .body("정상적으로 로그아웃 처리되었습니다.");
    }

    private ResponseEntity<TokenResDto> createTokenResponse(AuthTokens authTokens) {
        ResponseCookie cookie = CookieUtils.createRefreshTokenCookie(authTokens.getRefreshToken(), refreshTokenExpirationMs);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new TokenResDto(authTokens.getAccessToken()));
    }
}
