package MeshX.HypeLink.auth.controller;

import MeshX.HypeLink.auth.model.dto.AuthTokens;
import MeshX.HypeLink.auth.model.dto.LoginReqDto;
import MeshX.HypeLink.auth.model.dto.RegisterReqDto;
import MeshX.HypeLink.auth.model.dto.TokenDto;
import MeshX.HypeLink.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final long REFRESH_TOKEN_EXPIRE_TIME = 60 * 60 * 24 * 7;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterReqDto dto) {
        authService.register(dto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginReqDto dto, HttpServletResponse response) {
        AuthTokens authTokens = authService.login(dto);

        // Refresh Token을 HttpOnly 쿠키에 설정
        Cookie refreshTokenCookie = new Cookie("refresh_token", authTokens.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge((int) REFRESH_TOKEN_EXPIRE_TIME);

        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(new TokenDto(authTokens.getAccessToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> reissueTokens(@CookieValue("refresh_token") String refreshToken, HttpServletResponse response) {
        AuthTokens authTokens = authService.reissueTokens(refreshToken);

        Cookie refreshTokenCookie = new Cookie("refresh_token", authTokens.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge((int) REFRESH_TOKEN_EXPIRE_TIME);

        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(new TokenDto(authTokens.getAccessToken()));
    }
}
