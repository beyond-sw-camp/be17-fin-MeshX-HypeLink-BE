package MeshX.HypeLink.auth.exception;

import MeshX.HypeLink.common.exception.ExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthExceptionMessage implements ExceptionType {
    EMAIL_ALREADY_EXISTS("이미 존재하는 EMAIL 입니다.", "이미 존재하는 EMAIL 입니다 다시 입력해 주세요."),
    USER_NAME_NOT_FOUND("사용자를 찾을 수 없습니다.", "사용자를 찾을 수 없습니다. ID와 PASSWORD를 확인해주세요"),
    UNAUTHORIZED("인증 실패", "인증 자격 증명이 유효하지 않습니다. 로그인이 필요합니다."),
    ACCESS_DENIED("접근 거부", "해당 리소스에 접근할 권한이 없습니다.");


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
