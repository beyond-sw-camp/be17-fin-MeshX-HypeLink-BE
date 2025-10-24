package MeshX.HypeLink.head_office.customer.model.entity;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.direct_store.payment.model.entity.Payments;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerReceipt extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String pgProvider;   // ex) portone, toss

    @Column(nullable = false, length = 100)
    private String pgTid;        // imp_uid (포트원) or tid (토스)

    @Column(nullable = false, unique = true, length = 100)
    private String merchantUid;  // 우리 시스템 주문번호

    @Column(nullable = false)
    private Integer totalAmount;     // 총액

    private Integer amount;

    private Integer discountAmount;  // 할인액
    private Integer couponDiscount;  // 쿠폰 할인액

    @Column(nullable = false)
    private Integer finalAmount;     // 최종 결제 금액

    @JsonManagedReference
    @OneToMany(mappedBy = "customerReceipt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;

    // 비회원 정보
    private String memberName;
    private String memberPhone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @JsonIgnore
    @OneToOne(mappedBy = "customerReceipt")
    private Payments payments;

    private LocalDateTime paidAt;
    private LocalDateTime cancelledAt;

    @Builder
    public CustomerReceipt(String pgProvider, String pgTid, String merchantUid,
                           Integer amount, Integer totalAmount, Integer discountAmount,
                           Integer couponDiscount, Integer finalAmount,
                           Store store, Customer customer,
                           String memberName, String memberPhone,
                           PaymentStatus status,
                           LocalDateTime paidAt) {
        this.pgProvider = pgProvider;
        this.pgTid = pgTid;
        this.merchantUid = merchantUid;
        this.amount = amount;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.couponDiscount = couponDiscount;
        this.finalAmount = finalAmount;
        this.store = store;
        this.customer = customer;
        this.memberName = memberName;
        this.memberPhone = memberPhone;
        this.status = status;
        this.paidAt = paidAt;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setCustomerReceipt(this);
    }
}
