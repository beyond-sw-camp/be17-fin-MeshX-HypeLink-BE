package com.example.apidirect.item.adapter.out.entity;

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

    private String color;
    private String colorCode;
    private String size;
    private Integer stock;
    private String itemDetailCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private StoreItemEntity item;

    @Builder
    private StoreItemDetailEntity(String color, String colorCode, String size,
                                 Integer stock, String itemDetailCode, StoreItemEntity item) {
        this.color = color;
        this.colorCode = colorCode;
        this.size = size;
        this.stock = stock;
        this.itemDetailCode = itemDetailCode;
        this.item = item;
    }

    @Transient
    public String getCompositeKey() {
        if (item == null || item.getStoreId() == null) return null;
        return item.getStoreId() + "-" + itemDetailCode;
    }

    public void updateStock(Integer stockChange) {
        this.stock += stockChange;
    }
}
