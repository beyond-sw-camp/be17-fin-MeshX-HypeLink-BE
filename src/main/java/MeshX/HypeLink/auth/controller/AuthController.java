package MeshX.HypeLink.auth.controller;

import MeshX.HypeLink.auth.constansts.SwaggerConstants;
import MeshX.HypeLink.auth.model.dto.AuthTokens;
import MeshX.HypeLink.auth.model.dto.LoginReqDto;
import MeshX.HypeLink.auth.model.dto.RegisterReqDto;
import MeshX.HypeLink.auth.model.dto.TokenResDto;
import MeshX.HypeLink.auth.service.AuthService;
import MeshX.HypeLink.auth.utils.CookieUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 인증 및 권한", description = "시스템 사용자의 회원가입, 로그인, 로그아웃 및 토큰 재발급을 처리하는 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${jwt.refresh-token-expiration-ms}")
    private long refreshTokenExpirationMs;

    @Operation(summary = "회원가입", description = "새로운 사용자를 시스템에 등록합니다. 성공 시, Access Token과 Refresh Token(HTTP-Only Cookie)을 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResDto.class),
                            examples = @ExampleObject(value = SwaggerConstants.TOKEN_RESPONSE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않거나, 이미 존재하는 이메일입니다.", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<TokenResDto> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "가입할 사용자의 정보 (이메일, 비밀번호, 이름, 역할)",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterReqDto.class),
                            examples = @ExampleObject(value = SwaggerConstants.REGISTER_REQUEST)
                    )
            )
            @RequestBody RegisterReqDto dto) {
        AuthTokens authTokens = authService.register(dto);
        return createTokenResponse(authTokens, HttpStatus.CREATED);
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 사용자를 인증합니다. 성공 시, Access Token과 Refresh Token(HTTP-Only Cookie)을 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResDto.class),
                            examples = @ExampleObject(value = SwaggerConstants.TOKEN_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "인증 실패 (이메일 또는 비밀번호 불일치)", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<TokenResDto> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "로그인 정보 (이메일, 비밀번호)",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginReqDto.class),
                            examples = @ExampleObject(value = SwaggerConstants.LOGIN_REQUEST)
                    )
            )
            @RequestBody LoginReqDto dto) {
        AuthTokens authTokens = authService.login(dto);
        return createTokenResponse(authTokens, HttpStatus.OK);
    }

    @Operation(summary = "Access Token 재발급", description = "HTTP-Only Cookie에 담긴 Refresh Token을 사용하여 만료된 Access Token을 재발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResDto.class),
                            examples = @ExampleObject(value = SwaggerConstants.TOKEN_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 Refresh Token", content = @Content)
    })
    @PostMapping("/reissue")
    public ResponseEntity<TokenResDto> reissue(
            @Parameter(in = ParameterIn.COOKIE, name = "refresh_token", description = "Access Token 재발급에 사용되는 Refresh Token")
            @CookieValue("refresh_token") String refreshToken) {
        AuthTokens authTokens = authService.reissueTokens(refreshToken);
        return createTokenResponse(authTokens, HttpStatus.OK);
    }

    @Operation(summary = "로그아웃", description = "사용자를 로그아웃 처리합니다. 서버에 저장된 Refresh Token을 삭제하고, 클라이언트의 Refresh Token 쿠키를 만료시킵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 Access Token", content = @Content)
    })
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "로그아웃할 사용자의 Access Token (Bearer 형식)", required = true, example = "Bearer eyJhbGci...")
            @RequestHeader("Authorization") String authorizationHeader) {
        String accessToken = authorizationHeader.substring("Bearer ".length());
        authService.logout(accessToken);

        ResponseCookie emptyCookie = CookieUtils.createEmptyCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, emptyCookie.toString())
                .body("정상적으로 로그아웃 처리되었습니다.");
    }

    private ResponseEntity<TokenResDto> createTokenResponse(AuthTokens authTokens, HttpStatus status) {
        ResponseCookie cookie = CookieUtils.createRefreshTokenCookie(authTokens.getRefreshToken(), refreshTokenExpirationMs);

        return ResponseEntity.status(status)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new TokenResDto(authTokens.getAccessToken()));
    }
}
