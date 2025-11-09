package org.example.apidirect.item.adapter.out.entity;

import MeshX.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store_item_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreItemDetailEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String itemDetailCode;

    private String color;
    private String colorCode;
    private String size;

    @Column(nullable = false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private StoreItemEntity item;

    @Builder
    private StoreItemDetailEntity(String itemDetailCode,
                                 String color, String colorCode, String size,
                                 Integer stock, StoreItemEntity item) {
        this.itemDetailCode = itemDetailCode;
        this.color = color;
        this.colorCode = colorCode;
        this.size = size;
        this.stock = stock;
        this.item = item;
    }

    public void updateStock(Integer stockChange) {
        this.stock += stockChange;
    }
}
