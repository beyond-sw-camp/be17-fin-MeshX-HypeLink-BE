package MeshX.HypeLink.auth.controller;

import MeshX.HypeLink.auth.model.dto.*;
import MeshX.HypeLink.auth.service.AuthService;
import MeshX.HypeLink.auth.utils.CookieUtils;
import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${jwt.refresh-token-expiration-ms}")
    private long refreshTokenExpirationMs;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<String>> register(@RequestBody RegisterReqDto dto) {
        authService.register(dto);
        return ResponseEntity.ok(BaseResponse.of("회원가입이 성공하였습니다."));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResDto>> login(@RequestBody LoginReqDto dto) {
        LoginResDto result = authService.login(dto);

        ResponseCookie cookie = CookieUtils.createRefreshTokenCookie(result.getAuthTokens().getRefreshToken(), refreshTokenExpirationMs);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(BaseResponse.of(result, "로그인 성공"));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResDto> reissue(@CookieValue("refresh_token") String refreshToken) {
        AuthTokens authTokens = authService.reissueTokens(refreshToken);
        System.out.println("실행");
        System.out.println(authTokens);
        return createTokenResponse(authTokens);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
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
