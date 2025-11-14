package com.example.apidirect.item.adapter.out.entity;

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

    private String itemCode;

    @Column(nullable = false)
    private Integer storeId;

    private Integer unitPrice;
    private Integer amount;
    private String enName;
    private String koName;
    private String content;
    private String company;
    private String category;

    @Builder
    private StoreItemEntity(Integer id, String itemCode, Integer storeId,
                           Integer unitPrice, Integer amount, String enName,
                           String koName, String content, String company, String category) {
        this.id = id;
        this.itemCode = itemCode;
        this.storeId = storeId;
        this.unitPrice = unitPrice;
        this.amount = amount;
        this.enName = enName;
        this.koName = koName;
        this.content = content;
        this.company = company;
        this.category = category;
    }

    public void updateEnName(String enName) {
        this.enName = enName;
    }

    public void updateKoName(String koName) {
        this.koName = koName;
    }

    public void updateUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void updateCategory(String category) {
        this.category = category;
    }

    public void updateAmount(Integer amount) {
        this.amount = amount;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateCompany(String company) {
        this.company = company;
    }
}
