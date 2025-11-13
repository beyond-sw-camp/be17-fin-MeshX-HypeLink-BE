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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    private ColorEntity color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id")
    private SizeEntity size;

    @Column(nullable = false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private StoreItemEntity item;

    @Builder
    private StoreItemDetailEntity(Integer id, String itemDetailCode,
                                 ColorEntity color, SizeEntity size,
                                 Integer stock, StoreItemEntity item) {
        this.id = id;
        this.itemDetailCode = itemDetailCode;
        this.color = color;
        this.size = size;
        this.stock = stock;
        this.item = item;
    }

    public void updateStock(Integer stockChange) {
        this.stock += stockChange;
    }
}
