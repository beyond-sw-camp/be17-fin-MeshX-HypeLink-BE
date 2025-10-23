package MeshX.HypeLink.head_office.customer.exception;

import MeshX.HypeLink.common.exception.ExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CustomerExceptionType implements ExceptionType {
    NOT_FOUNT("찾을 수 없음", "찾을 수 없습니다"),
    ;

    private final String title;
    private final String messages;


    @Override
    public String title() {
        return title;
    }

    @Override
    public String message() {
        return title;
    }
}
