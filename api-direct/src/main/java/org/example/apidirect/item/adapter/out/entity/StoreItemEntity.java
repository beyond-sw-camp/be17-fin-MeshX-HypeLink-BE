package org.example.apidirect.item.adapter.out.entity;

import MeshX.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreItemEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String itemCode;

    private Integer unitPrice;
    private Integer amount;
    private String enName;
    private String koName;

    private String content;

    private String company;
    private String category;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreItemDetailEntity> itemDetails = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreItemImageEntity> itemImages = new ArrayList<>();

    @Builder
    private StoreItemEntity(Integer id, String itemCode, Integer unitPrice,
                           Integer amount, String enName, String koName,
                           String content, String company, String category) {
        this.id = id;
        this.itemCode = itemCode;
        this.unitPrice = unitPrice;
        this.amount = amount;
        this.enName = enName;
        this.koName = koName;
        this.content = content;
        this.company = company;
        this.category = category;
    }
}
