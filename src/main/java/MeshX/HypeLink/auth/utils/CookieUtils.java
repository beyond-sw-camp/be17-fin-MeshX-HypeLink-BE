package MeshX.HypeLink.auth.utils;

import org.springframework.http.ResponseCookie;

public class CookieUtils {

    /**
     * 리프레시 토큰을 담은 HttpOnly, Secure 쿠키를 생성합니다.
     * @param token 담을 리프레시 토큰
     * @param expirationMs 쿠키의 만료 시간 (밀리초)
     * @return ResponseCookie 객체
     */
    public static ResponseCookie createRefreshTokenCookie(String token, long expirationMs) {
        return ResponseCookie.from("refresh_token", token)
                .maxAge(expirationMs / 1000)
                .path("/")
                .secure(false) // 개발용 http 허용
                .httpOnly(true)
                .build();
    }

    /**
     * 기존의 리프레시 토큰 쿠키를 삭제하기 위한 빈 쿠키를 생성합니다.
     * @return maxAge가 0으로 설정된 ResponseCookie 객체
     */
    public static ResponseCookie createEmptyCookie() {
        return ResponseCookie.from("refresh_token", null)
                .maxAge(0)
                .path("/")
                .build();
    }
}
