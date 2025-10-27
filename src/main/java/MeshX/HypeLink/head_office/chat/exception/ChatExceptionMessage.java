package MeshX.HypeLink.head_office.chat.exception;

import MeshX.HypeLink.common.exception.ExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChatExceptionMessage implements ExceptionType {
    SENDER_NOT_FOUND("발신자 검색 에러", "발신자를 찾을 수 없습니다."),
    RECEIVER_NOT_FOUND("수신자 검색 에러", "수신자를 찾을 수 없습니다."),
    INVALID_MESSAGE_CONTENT("메시지 내용 에러", "메시지 내용이 비어있습니다.");

    private final String title;
    private final String message;

    @Override
    public String title() {
        return this.title;
    }

    @Override
    public String message() {
        return this.message;
    }
}
