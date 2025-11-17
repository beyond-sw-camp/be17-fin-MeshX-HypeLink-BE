package com.example.apidirect.item.adapter.out.mapper;

import com.example.apidirect.item.adapter.out.entity.StoreItemEntity;
import com.example.apidirect.item.adapter.out.entity.StoreItemImageEntity;
import com.example.apidirect.item.domain.StoreItemImage;

public class ItemImageMapper {

    public static StoreItemImage toDomain(StoreItemImageEntity entity) {
        if (entity == null) return null;

        return StoreItemImage.builder()
                .id(entity.getId())
                .sortIndex(entity.getSortIndex())
                .originalFilename(entity.getOriginalFilename())
                .savedPath(entity.getSavedPath())
                .contentType(entity.getContentType())
                .fileSize(entity.getFileSize())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static StoreItemImageEntity toEntity(StoreItemImage domain, StoreItemEntity item) {
        if (domain == null) return null;

        return StoreItemImageEntity.builder()
                .originalFilename(domain.getOriginalFilename())
                .savedPath(domain.getSavedPath())
                .contentType(domain.getContentType())
                .fileSize(domain.getFileSize())
                .sortIndex(domain.getSortIndex())
                .item(item)
                .build();
    }
}
