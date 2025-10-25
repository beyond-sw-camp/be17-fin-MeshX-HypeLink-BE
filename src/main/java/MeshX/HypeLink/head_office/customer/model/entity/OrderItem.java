package MeshX.HypeLink.head_office.customer.model.entity;

import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import MeshX.HypeLink.head_office.item.model.entity.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference; // Added import

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonBackReference // Added annotation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_receipt_id", nullable = false)
    private CustomerReceipt customerReceipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_item_id", nullable = false)
    private StoreItem storeItem;

    @Column(nullable = false)
    private Integer quantity;     // 구매 수량

    @Column(nullable = false)
    private Integer unitPrice;    // 결제 시점 단가

    @Column(nullable = false)
    private Integer totalPrice;   // 결제 시점 총액

    @Builder
    public OrderItem(StoreItem storeItem, Integer quantity, Integer unitPrice, Integer totalPrice) {
        this.storeItem = storeItem;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    // CustomerReceipt 설정 (양방향 관계)
    protected void setCustomerReceipt(CustomerReceipt customerReceipt) {
        this.customerReceipt = customerReceipt;
    }
}
