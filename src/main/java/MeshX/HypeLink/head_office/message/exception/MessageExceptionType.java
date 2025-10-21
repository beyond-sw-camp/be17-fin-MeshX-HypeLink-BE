package MeshX.HypeLink.head_office.message.exception;

import MeshX.HypeLink.common.exception.ExceptionType;

public enum MessageExceptionType implements ExceptionType {
    MESSAGE_NOT_FOUND("메시지 검색 에러","메시지를 찾을 수 없습니다.")





    ;

    private final String title;
    private final String message;

    MessageExceptionType(String title, String message) {
        this.title = title;
        this.message = message;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public String message() {
        return message;
    }
}
