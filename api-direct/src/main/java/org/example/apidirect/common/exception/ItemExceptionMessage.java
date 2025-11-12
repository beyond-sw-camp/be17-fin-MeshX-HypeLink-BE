package org.example.apidirect.common.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ItemExceptionMessage implements ExceptionType {
    NOT_FOUND("가맹점 상품 Error", "해당하는 상품이 존재하지 않습니다. 다시 입력해주세요."),
    INVALID_PAGE("페이징(page) Error", "page는 0 이상의 정수여야 합니다."),
    INVALID_PAGE_SIZE("페이징(pageSize) Error", "pageSize는 1 이상의 정수여야 합니다."),
    INSUFFICIENT_STOCK("재고 부족 Error", "재고가 부족하여 처리할 수 없습니다."),
    INVALID_STOCK_UPDATE("재고 업데이트 Error", "재고 업데이트 값이 유효하지 않습니다.");

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
