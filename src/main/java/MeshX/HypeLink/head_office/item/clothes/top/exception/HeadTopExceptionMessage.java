package MeshX.HypeLink.head_office.item.clothes.top.exception;

import MeshX.HypeLink.common.exception.ExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HeadTopExceptionMessage implements ExceptionType {
    NOT_FOUND("상의 Error", "해당하는 상의 품목이 존재하지 않습니다. 다시 입력해주세요.")
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
