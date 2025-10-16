package MeshX.HypeLink.direct_store.posOrder.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PosOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String orderNumber;

    private Integer storeId;

    private Integer memberId;
    private String memberName;
    private String memberPhone;

    @Column(nullable = false)
    private Integer totalAmount;

    private Integer discountAmount;
    private Integer pointsUsed;
    private Integer couponDiscount;

    @Column(nullable = false)
    private Integer finalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PosOrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PosOrderItem> PosOrderItems = new ArrayList<>();

    @Builder
    private PosOrder(String orderNumber, Integer storeId, Integer memberId, String memberName,
                  String memberPhone, Integer totalAmount, Integer discountAmount,
                  Integer pointsUsed, Integer couponDiscount, Integer finalAmount,
                  PosOrderStatus status) {
        this.orderNumber = orderNumber;
        this.storeId = storeId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberPhone = memberPhone;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.pointsUsed = pointsUsed;
        this.couponDiscount = couponDiscount;
        this.finalAmount = finalAmount;
        this.status = status != null ? status : PosOrderStatus.PENDING;
    }

    // 주문 상품 추가
    public void addOrderItem(PosOrderItem posOrderItem) {
        this.PosOrderItems.add(posOrderItem);
        posOrderItem.setOrder(this);
    }

    // 상태 변경
    public void updateStatus(PosOrderStatus status) {
        this.status = status;
    }

    // 금액 재계산
    public void recalculateAmounts() {
        this.totalAmount = PosOrderItems.stream()
                .mapToInt(PosOrderItem::getSubtotal)
                .sum();

        this.finalAmount = this.totalAmount
                - (this.discountAmount != null ? this.discountAmount : 0)
                - (this.pointsUsed != null ? this.pointsUsed : 0)
                - (this.couponDiscount != null ? this.couponDiscount : 0);
    }
}