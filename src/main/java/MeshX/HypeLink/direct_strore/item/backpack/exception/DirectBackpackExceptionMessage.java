package MeshX.HypeLink.direct_strore.item.backpack.exception;

import MeshX.HypeLink.common.exception.ExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DirectBackpackExceptionMessage implements ExceptionType {
    NOT_FOUND("가방 Error", "해당하는 가방 품목이 존재하지 않습니다. 다시 입력해주세요.")
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
