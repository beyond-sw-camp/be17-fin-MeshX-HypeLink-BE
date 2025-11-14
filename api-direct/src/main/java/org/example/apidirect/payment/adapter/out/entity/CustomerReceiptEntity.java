package org.example.apidirect.payment.adapter.out.entity;

import MeshX.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.apidirect.payment.domain.PaymentStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_receipt")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerReceiptEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String pgProvider;

    @Column(nullable = false, length = 100)
    private String pgTid;

    @Column(nullable = false, unique = true, length = 100)
    private String merchantUid;

    @Column(nullable = false)
    private Integer totalAmount;

    private Integer discountAmount;
    private Integer couponDiscount;

    @Column(nullable = false)
    private Integer finalAmount;

    @OneToMany(mappedBy = "customerReceipt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    @Column(name = "store_id")
    private Integer storeId;

    @Column(name = "customer_id")
    private Integer customerId;

    private String memberName;
    private String memberPhone;

    @Column(length = 50)
    private String posCode;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDateTime paidAt;
    private LocalDateTime cancelledAt;

    @Builder
    private CustomerReceiptEntity(Integer id, String pgProvider, String pgTid,
                                  String merchantUid, Integer totalAmount,
                                  Integer discountAmount, Integer couponDiscount,
                                  Integer finalAmount, Integer storeId, Integer customerId,
                                  String memberName, String memberPhone, String posCode,
                                  PaymentStatus status, LocalDateTime paidAt) {
        this.id = id;
        this.pgProvider = pgProvider;
        this.pgTid = pgTid;
        this.merchantUid = merchantUid;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.couponDiscount = couponDiscount;
        this.finalAmount = finalAmount;
        this.storeId = storeId;
        this.customerId = customerId;
        this.memberName = memberName;
        this.memberPhone = memberPhone;
        this.posCode = posCode;
        this.status = status;
        this.paidAt = paidAt;
    }

    public void addOrderItem(OrderItemEntity orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setCustomerReceipt(this);
    }

    public void cancel() {
        this.status = PaymentStatus.CANCELLED;
        this.cancelledAt = LocalDateTime.now();
    }
}
