package MeshX.HypeLink.direct_store.item.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreItemDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String color;
    private String colorCode;
    private String size;
    private Integer stock; // 재고
//    @Column(unique = true, nullable = false)
    private String itemDetailCode; // 아이템 코드 + 색상 + 사이즈

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private StoreItem item;

    @Builder
    private StoreItemDetail(String color, String colorCode, String size, Integer stock,
                            String itemDetailCode, StoreItem item) {
        this.color = color;
        this.colorCode = colorCode;
        this.size = size;
        this.stock = stock;
        this.itemDetailCode = itemDetailCode;
        this.item = item;
    }

    @Transient
    public String getCompositeKey() {
        if (item == null || item.getStore() == null) return null;
        return item.getStore().getId() + "-" + itemDetailCode;
    }

    public void updateStock(Integer stock) {
        this.stock += stock;
    }
}
