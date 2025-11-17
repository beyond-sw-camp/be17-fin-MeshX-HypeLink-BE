package com.example.apidirect.payment.domain;

public enum PaymentStatus {
    PENDING,      // 대기 중
    PAID,         // 결제 완료
    FAILED,       // 결제 실패
    CANCELLED,    // 취소
    REFUNDED      // 환불
}
