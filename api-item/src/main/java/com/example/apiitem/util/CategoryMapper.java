package com.example.apiitem.util;

import com.example.apiitem.item.adaptor.out.jpa.CategoryEntity;
import com.example.apiitem.item.domain.Category;

public class CategoryMapper {
    public static Category toDomain(CategoryEntity entity) {
        return Category.builder()
                .id(entity.getId())
                .category(entity.getCategory())
                .build();
    }
}
