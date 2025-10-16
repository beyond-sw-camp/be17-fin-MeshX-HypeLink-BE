package MeshX.HypeLink.direct_store.payment.exception;

import MeshX.HypeLink.common.exception.ExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentExceptionMessage implements ExceptionType {
    PAYMENT_NOT_FOUND("결제 Error", "해당하는 결제 정보가 존재하지 않습니다."),
    PAYMENT_VALIDATION_FAILED("결제 Error", "결제 검증에 실패했습니다."),
    PAYMENT_AMOUNT_MISMATCH("결제 Error", "결제 금액이 일치하지 않습니다."),
    PAYMENT_STATUS_INVALID("결제 Error", "유효하지 않은 결제 상태입니다."),
    PORTONE_API_ERROR("PortOne Error", "PortOne API 호출 중 오류가 발생했습니다.")
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