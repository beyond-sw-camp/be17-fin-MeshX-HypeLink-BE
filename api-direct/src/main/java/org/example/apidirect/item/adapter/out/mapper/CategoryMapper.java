package org.example.apidirect.item.adapter.out.mapper;

import org.example.apidirect.item.adapter.out.entity.CategoryEntity;
import org.example.apidirect.item.domain.Category;

public class CategoryMapper {

    public static Category toDomain(CategoryEntity entity) {
        if (entity == null) return null;

        return Category.builder()
                .id(entity.getId())
                .category(entity.getCategory())
                .build();
    }

    public static CategoryEntity toEntity(Category domain) {
        if (domain == null) return null;

        return CategoryEntity.builder()
                .category(domain.getCategory())
                .build();
    }

    public static CategoryEntity toEntity(String category) {
        if (category == null) return null;

        return CategoryEntity.builder()
                .category(category)
                .build();
    }
}
