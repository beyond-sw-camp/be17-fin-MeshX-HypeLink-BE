package com.example.apiitem.util;

import com.example.apiitem.item.adaptor.out.jpa.CategoryEntity;
import com.example.apiitem.item.domain.Category;
import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaCategory;

public class CategoryMapper {
    public static CategoryEntity toEntity(Category domain) {
        return CategoryEntity.builder()
                .id(domain.getId())
                .category(domain.getCategory())
                .build();
    }

    public static Category toDomain(KafkaCategory dto) {
        return Category.builder()
                .id(dto.getId())
                .category(dto.getCategory())
                .build();
    }

    public static Category toDomain(CategoryEntity entity) {
        return Category.builder()
                .id(entity.getId())
                .category(entity.getCategory())
                .build();
    }
}
