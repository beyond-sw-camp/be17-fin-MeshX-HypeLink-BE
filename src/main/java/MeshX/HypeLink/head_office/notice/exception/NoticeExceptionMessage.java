package MeshX.HypeLink.head_office.notice.exception;

import MeshX.HypeLink.common.exception.ExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NoticeExceptionMessage implements ExceptionType {
    NOT_FOUND("공지사항 Error", "해당하는 공지사항이 존재하지 않습니다. 다시 입력해주세요.")
    ;

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
