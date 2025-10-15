package MeshX.HypeLink.direct_store.item.clothes.outer.exception;

import MeshX.HypeLink.common.exception.ExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DirectOuterExceptionMessage implements ExceptionType {
    NOT_FOUND("아우터 Error", "해당하는 아우터 품목이 존재하지 않습니다. 다시 입력해주세요.")
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
