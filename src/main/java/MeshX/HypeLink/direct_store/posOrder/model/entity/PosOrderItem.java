package MeshX.HypeLink.direct_store.posOrder.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PosOrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private PosOrder order;

    @Column(nullable = false)
    private Integer productId;

    @Column(nullable = false, length = 100)
    private String productName;
    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer unitPrice;

    private Integer discountPrice;

    @Column(nullable = false)
    private Integer subtotal;        // 소계

    @Builder
    private PosOrderItem(Integer productId, String productName, Integer quantity,
                         Integer unitPrice, Integer discountPrice, Integer subtotal) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.discountPrice = discountPrice;
        this.subtotal = subtotal;
    }

    // Order 설정 (양방향 관계)
    protected void setOrder(PosOrder order) {
        this.order = order;
    }

    // 수량 변경
    public void updateQuantity(Integer quantity) {
        this.quantity = quantity;
        calculateSubtotal();
    }

    // 할인가 적용
    public void applyDiscount(Integer discountPrice) {
        this.discountPrice = discountPrice;
        calculateSubtotal();
    }

    // 소계 계산
    private void calculateSubtotal() {
        Integer price = (discountPrice != null && discountPrice > 0)
                ? discountPrice : unitPrice;
        this.subtotal = price * quantity;
    }
}