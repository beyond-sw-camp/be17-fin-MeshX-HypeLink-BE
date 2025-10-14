package MeshX.HypeLink.auth.controller;

import MeshX.HypeLink.auth.model.dto.AuthTokens;
import MeshX.HypeLink.auth.model.dto.LoginReqDto;
import MeshX.HypeLink.auth.model.dto.RegisterReqDto;
import MeshX.HypeLink.auth.model.dto.TokenResDto;
import MeshX.HypeLink.auth.service.AuthService;
import MeshX.HypeLink.auth.utils.CookieUtils;
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
    public ResponseEntity<TokenResDto> register(@RequestBody RegisterReqDto dto) {
        AuthTokens authTokens = authService.register(dto);
        return createTokenResponse(authTokens);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResDto> login(@RequestBody LoginReqDto dto) {
        AuthTokens authTokens = authService.login(dto);
        return createTokenResponse(authTokens);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResDto> reissue(@CookieValue("refresh_token") String refreshToken) {
        AuthTokens authTokens = authService.reissueTokens(refreshToken);
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
