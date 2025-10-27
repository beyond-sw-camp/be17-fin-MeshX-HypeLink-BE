package MeshX.HypeLink.head_office.as.exception;

import MeshX.HypeLink.common.exception.ExceptionType;

public enum AsExceptionMessage implements ExceptionType {

    AS_COMMENT_NOT_FOUNT("AS 코멘트 검색 에러","해당 코멘트을 찾을 수 없습니다"),
    AS_NOT_FOUNT("AS 검색 에러","해당 게시글을 찾을 수 없습니다"),
    AS_UNAUTHORIZED("AS 권한 에러", "본인의 AS 요청만 수정/삭제할 수 있습니다"),
    AS_CANNOT_MODIFY("AS 수정 에러", "대기중 상태의 AS만 수정/삭제할 수 있습니다"),
    NO_PERMISSION("권환 없음", "총 관리자 혹은 중간 관리자만 작성할 수 있습니다.");


    private final String title;
    private final String message;

    AsExceptionMessage(String title, String message) {
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
