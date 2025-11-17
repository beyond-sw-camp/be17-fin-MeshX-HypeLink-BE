package com.example.apidirect.payment.usecase.port.in.request;

import lombok.Getter;

@Getter
public class PaymentValidationCommand {
    private String name;             // 멤버 이름
    private String paymentId;        // PortOne 결제 ID
    private ReceiptCreateCommand orderData;  // 영수증 데이터
}