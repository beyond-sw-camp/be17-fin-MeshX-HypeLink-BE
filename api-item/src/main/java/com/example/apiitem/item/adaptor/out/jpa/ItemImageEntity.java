package com.example.apiitem.item.adaptor.out.jpa;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemImageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    private Integer sortIndex;
    private String originalFilename;
    private String savedPath;
    private String contentType;
    private Long fileSize;

    @Builder
    public ItemImageEntity(Integer id, ItemEntity item, Integer sortIndex, String originalFilename,
                           String savedPath, String contentType, Long fileSize) {
        this.id = id;
        this.item = item;
        this.sortIndex = sortIndex;
        this.originalFilename = originalFilename;
        this.savedPath = savedPath;
        this.contentType = contentType;
        this.fileSize = fileSize;
    }

    public void updateIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }
}
