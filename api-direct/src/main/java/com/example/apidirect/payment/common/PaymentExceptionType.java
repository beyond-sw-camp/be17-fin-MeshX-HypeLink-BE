package com.example.apidirect.payment.common;

import MeshX.common.exception.ExceptionType;
import lombok.Builder;

@Builder
public class PaymentExceptionType implements ExceptionType {
    private final String title;
    private final String message;

    @Override
    public String title() {
        return title;
    }

    @Override
    public String message() {
        return message;
    }

    // 결제 관련
    public static final PaymentExceptionType PAYMENT_NOT_FOUND = PaymentExceptionType.builder()
            .title("결제 Error")
            .message("해당하는 결제 정보가 존재하지 않습니다.")
            .build();

    public static final PaymentExceptionType PAYMENT_VALIDATION_FAILED = PaymentExceptionType.builder()
            .title("결제 Error")
            .message("결제 검증에 실패했습니다.")
            .build();

    public static final PaymentExceptionType PAYMENT_AMOUNT_MISMATCH = PaymentExceptionType.builder()
            .title("결제 Error")
            .message("결제 금액이 일치하지 않습니다.")
            .build();

    public static final PaymentExceptionType PAYMENT_STATUS_INVALID = PaymentExceptionType.builder()
            .title("결제 Error")
            .message("유효하지 않은 결제 상태입니다.")
            .build();

    // 외부 API 관련
    public static final PaymentExceptionType PORTONE_API_ERROR = PaymentExceptionType.builder()
            .title("PortOne Error")
            .message("PortOne API 호출 중 오류가 발생했습니다.")
            .build();

    // 재고/상품 관련
    public static final PaymentExceptionType INSUFFICIENT_STOCK = PaymentExceptionType.builder()
            .title("재고 Error")
            .message("재고가 부족합니다.")
            .build();

    public static final PaymentExceptionType ITEM_NOT_FOUND = PaymentExceptionType.builder()
            .title("상품 Error")
            .message("해당하는 상품이 존재하지 않습니다.")
            .build();

    // 주문/결제 처리
    public static final PaymentExceptionType ORDER_PROCESSING_ERROR = PaymentExceptionType.builder()
            .title("주문 Error")
            .message("주문 처리 중 오류가 발생했습니다.")
            .build();

    public static final PaymentExceptionType PAYMENT_CANCEL_ERROR = PaymentExceptionType.builder()
            .title("결제 취소 Error")
            .message("결제 취소 중 오류가 발생했습니다.")
            .build();

    // POS 관련
    public static final PaymentExceptionType POS_NOT_FOUND = PaymentExceptionType.builder()
            .title("POS Error")
            .message("해당 회원의 POS 정보를 찾을 수 없습니다.")
            .build();

    // Store 관련
    public static final PaymentExceptionType STORE_NOT_FOUND = PaymentExceptionType.builder()
            .title("Store Error")
            .message("매장 정보를 찾을 수 없습니다.")
            .build();
}
