package org.example.apiauth.common.exception;

import MeshX.common.exception.ExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TokenExceptionMessage implements ExceptionType {
    INVALID_SIGNATURE("잘못된 JWT 서명입니다.", "잘못된 JWT 서명입니다."),
    EXPIRED_TOKEN("만료된 JWT 토큰입니다.", "만료된 JWT 토큰입니다. 다시 로그인해주세요."),
    UNSUPPORTED_TOKEN("지원되지 않는 JWT 토큰입니다.", "지원되지 않는 JWT 토큰입니다."),
    INVALID_TOKEN("JWT 토큰이 잘못되었습니다.", "JWT 토큰이 잘못되었습니다."),
    TOKEN_NOT_FOUND("토큰이 존재하지 않습니다.", "토큰이 존재하지 않습니다. 다시 로그인해주세요."),
    BLACKLISTED_TOKEN("로그아웃 처리된 토큰입니다.", "로그아웃 처리된 토큰입니다. 다시 로그인해주세요.");

    private final String title;
    private final String messages;

    @Override
    public String title() {
        return this.title;
    }

    @Override
    public String message() {
        return this.messages;
    }
}
