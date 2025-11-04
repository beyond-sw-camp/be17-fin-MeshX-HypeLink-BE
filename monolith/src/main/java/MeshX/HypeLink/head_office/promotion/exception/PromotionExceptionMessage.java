package MeshX.HypeLink.head_office.promotion.exception;

import MeshX.HypeLink.common.exception.ExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PromotionExceptionMessage implements ExceptionType {
    NOT_FOUND("프로모션 Error", "프로모션이 존재하지 않습니다. 다시 입력해주세요."),
    INVALID_PAGE("페이징(page) Error", "page는 0 이상의 정수여야 합니다."),
    INVALID_PAGE_SIZE("페이징(pageSize) Error", "pageSize는 1 이상의 정수여야 합니다.");
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
