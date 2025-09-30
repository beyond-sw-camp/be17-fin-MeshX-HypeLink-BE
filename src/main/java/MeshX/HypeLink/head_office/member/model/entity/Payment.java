package MeshX.HypeLink.head_office.member.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.head_office.store.model.entity.StoreMember;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, length = 50)
    private String pgProvider;   // ex) portone, toss

    @Column(nullable = false, length = 100)
    private String pgTid;        // imp_uid (포트원) or tid (토스)

    @Column(nullable = false, unique = true, length = 100)
    private String merchantUid;  // 우리 시스템 주문번호

    @Column(nullable = false)
    private Integer amount;

    @OneToMany(mappedBy = "payment")
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "purchase_store_id")
    private StoreMember purchaseStore;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String payMethod;
    private String cardName;
    private String cardNumber;

    private LocalDateTime paidAt;
    private LocalDateTime cancelledAt;

    public void setPurchaseStore(StoreMember purchaseStore) {
        this.purchaseStore = purchaseStore;
    }
}
