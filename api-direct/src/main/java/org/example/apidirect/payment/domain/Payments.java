package org.example.apidirect.payment.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Payments {
    private Integer id;
    private Integer customerReceiptId;
    private String paymentId;
    private String transactionId;
    private String storeId;
    private String channelKey;
    private Integer amount;
    private PaymentStatus status;
    private LocalDateTime paidAt;
    private String failureReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void cancel(String reason) {
        if (this.status == PaymentStatus.CANCELLED || this.status == PaymentStatus.REFUNDED) {
            throw new IllegalStateException("이미 취소 또는 환불된 결제입니다");
        }
        this.status = PaymentStatus.CANCELLED;
        this.failureReason = reason;
    }

    public void complete(LocalDateTime paidAt, String transactionId) {
        this.status = PaymentStatus.PAID;
        this.paidAt = paidAt;
        this.transactionId = transactionId;
    }

    public void fail(String reason) {
        this.status = PaymentStatus.FAILED;
        this.failureReason = reason;
    }

    public void validateAmount() {
        if (this.amount == null || this.amount <= 0) {
            throw new IllegalArgumentException("결제 금액은 0보다 커야 합니다");
        }
    }

    public boolean isRefundable() {
        return this.status == PaymentStatus.PAID;
    }

    public boolean isPaid() {
        return this.status == PaymentStatus.PAID;
    }
}
