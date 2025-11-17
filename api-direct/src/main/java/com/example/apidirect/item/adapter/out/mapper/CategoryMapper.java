package com.example.apidirect.item.adapter.out.mapper;

import com.example.apidirect.auth.adapter.out.entity.StoreEntity;
import com.example.apidirect.item.adapter.out.entity.StoreCategoryEntity;
import com.example.apidirect.item.domain.Category;

public class CategoryMapper {

    public static Category toDomain(StoreCategoryEntity entity) {
        if (entity == null) return null;

        return Category.builder()
                .id(entity.getId())
                .category(entity.getCategory())
                .storeId(entity.getStore() != null ? entity.getStore().getId() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static StoreCategoryEntity toEntity(Category domain, StoreEntity store) {
        if (domain == null) return null;

        return StoreCategoryEntity.builder()
                .id(domain.getId())
                .category(domain.getCategory())
                .store(store)
                .build();
    }

    public static StoreCategoryEntity toEntity(String category, StoreEntity store) {
        if (category == null) return null;

        return StoreCategoryEntity.builder()
                .category(category)
                .store(store)
                .build();
    }
}
