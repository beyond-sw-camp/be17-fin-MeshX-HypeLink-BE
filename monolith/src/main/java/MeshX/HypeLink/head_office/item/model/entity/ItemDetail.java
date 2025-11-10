package MeshX.HypeLink.head_office.item.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id")
    private Size size;

    @Column(unique = true, nullable = false)
    private String itemDetailCode; // 아이템 코드 + 색상 + 사이즈
    private Integer stock; // 재고

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    private ItemDetail(Color color, Size size, String itemDetailCode, Integer stock,
                       Item item, Integer id) {
        this.color = color;
        this.size = size;
        this.itemDetailCode = itemDetailCode;
        this.stock = stock;
        this.item = item;
        this.id = id;
    }

    public void updateStock(Integer stock){
        this.stock += stock;
    }
}
