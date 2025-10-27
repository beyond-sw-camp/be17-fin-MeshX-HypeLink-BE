package MeshX.HypeLink.head_office.order.exception;

import MeshX.HypeLink.common.exception.ExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PurchaseOrderExceptionMessage implements ExceptionType {
    NOT_FOUND("본사주문 Error", "해당하는 주문이 존재하지 않습니다. 다시 입력해주세요."),
    UNDER_ZERO("본사주문 Error", "재고 초과하는 주문입니다. 다시 주문해주세요.")
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

