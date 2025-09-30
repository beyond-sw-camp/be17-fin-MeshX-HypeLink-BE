package MeshX.HypeLink.head_office.member.model.entity;

import MeshX.HypeLink.direct_strore.item.DirectItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private DirectItem item;

    @Column(nullable = false)
    private Integer quantity;     // 구매 수량

    @Column(nullable = false)
    private Integer unitPrice;    // 결제 시점 단가

    @Column(nullable = false)
    private Integer totalPrice;   // 결제 시점 총액
}
