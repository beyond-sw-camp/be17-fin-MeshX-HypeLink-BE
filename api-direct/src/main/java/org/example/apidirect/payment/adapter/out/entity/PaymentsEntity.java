package org.example.apidirect.payment.adapter.out.entity;

import MeshX.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.apidirect.payment.domain.PaymentStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customer_receipt_id", unique = true)
    private Integer customerReceiptId;

    @Column(unique = true, length = 100)
    private String paymentId;

    private String transactionId;

    private String storeId;
    private String channelKey;

    @Column(nullable = false)
    private Integer amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status;

    private LocalDateTime paidAt;

    @Column(length = 500)
    private String failureReason;

    @Builder
    private PaymentsEntity(Integer id, Integer customerReceiptId, String paymentId,
                          String transactionId, String storeId, String channelKey,
                          Integer amount, PaymentStatus status, LocalDateTime paidAt,
                          String failureReason) {
        this.id = id;
        this.customerReceiptId = customerReceiptId;
        this.paymentId = paymentId;
        this.transactionId = transactionId;
        this.storeId = storeId;
        this.channelKey = channelKey;
        this.amount = amount;
        this.status = status != null ? status : PaymentStatus.PENDING;
        this.paidAt = paidAt;
        this.failureReason = failureReason;
    }

    public void updateStatus(PaymentStatus status) {
        this.status = status;
    }

    public void setFailureReason(String reason) {
        this.failureReason = reason;
    }
}
