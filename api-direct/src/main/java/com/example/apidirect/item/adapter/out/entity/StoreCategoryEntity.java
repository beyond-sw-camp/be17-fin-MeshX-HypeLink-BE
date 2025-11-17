package com.example.apidirect.item.adapter.out.entity;

import MeshX.common.BaseEntity;
import com.example.apidirect.auth.adapter.out.entity.StoreEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreCategoryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @Builder
    private StoreCategoryEntity(Integer id, String category, StoreEntity store) {
        this.id = id;
        this.category = category;
        this.store = store;
    }
}
