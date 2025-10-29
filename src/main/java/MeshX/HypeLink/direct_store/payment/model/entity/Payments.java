package MeshX.HypeLink.direct_store.payment.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.head_office.customer.model.entity.CustomerReceipt;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payments extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", unique = true)
    private CustomerReceipt customerReceipt;

    @Column(unique = true, length = 100)
    private String paymentId;        // PortOne 결제 ID

    private String transactionId;    // PortOne 거래 ID

    private String storeId;          // PortOne Store ID
    private String channelKey;       // PortOne Channel Key

    @Column(nullable = false)
    private Integer amount;          // 결제 금액

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status;    // 결제 상태

    private LocalDateTime paidAt;    // 결제 완료 시각

    @Column(length = 500)
    private String failureReason;    // 실패 사유

    @Builder
    private Payments(CustomerReceipt customerReceipt, String paymentId, String transactionId,
                     String storeId, String channelKey,
                     Integer amount, PaymentStatus status, LocalDateTime paidAt,
                     String failureReason) {
        this.customerReceipt = customerReceipt;
        this.paymentId = paymentId;
        this.transactionId = transactionId;
        this.storeId = storeId;
        this.channelKey = channelKey;
        this.amount = amount;
        this.status = status != null ? status : PaymentStatus.PENDING;
        this.paidAt = paidAt;
        this.failureReason = failureReason;
    }

    // 상태 변경
    public void updateStatus(PaymentStatus status) {
        this.status = status;
    }

    // 실패 사유 설정
    public void setFailureReason(String reason) {
        this.failureReason = reason;
    }

    // 결제 완료 처리
    public void completePay(LocalDateTime paidAt, String transactionId) {
        this.status = PaymentStatus.PAID;
        this.paidAt = paidAt;
        this.transactionId = transactionId;
    }
}
