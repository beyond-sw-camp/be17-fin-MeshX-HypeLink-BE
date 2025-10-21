package MeshX.HypeLink.head_office.order.model.entity;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.head_office.item.model.entity.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseOrder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantity;        // 수량
    private Integer totalPrice;      // 총액

    @Enumerated(EnumType.STRING)
    private PurchaseOrderState purchaseOrderState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private Member requester;       // 발주를 요청한 본사 or 매장

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Member supplier;        // 발주를 수락하고 공급하는 쪽

    @Builder
    private PurchaseOrder(Integer quantity, Integer totalPrice, PurchaseOrderState purchaseOrderState, Item item,
                          Member requester, Member supplier) {
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.purchaseOrderState = purchaseOrderState;
        this.item = item;
        this.requester = requester;
        this.supplier = supplier;
    }

    public void updateOrderState(PurchaseOrderState purchaseOrderState) {
        this.purchaseOrderState = purchaseOrderState;
    }
}

