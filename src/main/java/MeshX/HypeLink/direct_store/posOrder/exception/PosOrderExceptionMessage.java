package MeshX.HypeLink.direct_store.posOrder.exception;

import MeshX.HypeLink.common.exception.ExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PosOrderExceptionMessage implements ExceptionType {
    ORDER_NOT_FOUND("주문 Error", "해당하는 주문이 존재하지 않습니다."),
    ORDER_LIST_EMPTY("주문 Error", "주문 목록이 비어있습니다."),
    ORDER_ALREADY_PAID("주문 Error", "이미 결제된 주문입니다."),
    ORDER_CANNOT_CANCEL("주문 Error", "취소할 수 없는 주문입니다."),
    INVALID_ORDER_STATUS("주문 Error", "유효하지 않은 주문 상태입니다."),
    ORDER_ITEMS_EMPTY("주문 Error", "주문 상품이 비어있습니다.")
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
