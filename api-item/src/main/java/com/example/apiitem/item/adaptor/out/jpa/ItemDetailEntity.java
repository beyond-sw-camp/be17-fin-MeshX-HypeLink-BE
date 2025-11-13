package com.example.apiitem.item.adaptor.out.jpa;

import MeshX.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemDetailEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    private ColorEntity color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id")
    private SizeEntity size;

    @Column(unique = true, nullable = false)
    private String itemDetailCode; // 아이템 코드 + 색상 + 사이즈
    private Integer stock; // 재고

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    @Builder
    private ItemDetailEntity(Integer id, ColorEntity color, SizeEntity size, String itemDetailCode,
                             Integer stock, ItemEntity item) {
        this.id = id;
        this.color = color;
        this.size = size;
        this.itemDetailCode = itemDetailCode;
        this.stock = stock;
        this.item = item;
    }

    public void updateStock(Integer stock){
        this.stock += stock;
    }
}
