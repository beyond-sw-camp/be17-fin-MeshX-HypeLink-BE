package org.example.apiauth.common.utils;

import org.springframework.http.ResponseCookie;

public class CookieUtils {

    // TODO: [배포] 실제 프로덕션 배포 시에는 아래 secure(true)와 sameSite("None")으로 변경해야 합니다. (HTTPS 필수)
    public static ResponseCookie createRefreshTokenCookie(String token, long expirationMs) {
        return ResponseCookie.from("refresh_token", token)
                .maxAge(expirationMs / 1000)
                .path("/")
                .secure(false) // 개발용 http 허용
                .sameSite("Lax") // http 개발 환경을 위한 설정
                .httpOnly(true)
                .build();
    }
    public static ResponseCookie createEmptyCookie() {
        return ResponseCookie.from("refresh_token", null)
                .maxAge(0)
                .path("/")
                .build();
    }

}
