package MeshX.HypeLink.direct_strore.item.shoes.exception;

import MeshX.HypeLink.common.exception.ExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DirectShoesExceptionMessage implements ExceptionType {
    NOT_FOUND("신발 Error", "해당하는 신발 품목이 존재하지 않습니다. 다시 입력해주세요.")
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
