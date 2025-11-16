package com.example.apidirect.item.adapter.out.entity;

import MeshX.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store_item_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreItemImageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer sortIndex;
    private String originalFilename;
    private String savedPath;
    private String contentType;
    private Long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private StoreItemEntity item;

    @Builder
    private StoreItemImageEntity(String originalFilename, String savedPath,
                                String contentType, Long fileSize,
                                Integer sortIndex, StoreItemEntity item) {
        this.originalFilename = originalFilename;
        this.savedPath = savedPath;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.sortIndex = sortIndex;
        this.item = item;
    }

    @Transient
    public String getCompositeKey() {
        if (item == null || item.getStore() == null) return null;
        return item.getStore() + "-" + originalFilename;
    }

    public void updateIndex(Integer index) {
        this.sortIndex = index;
    }
}
