package com.example.apiitem.item.domain;

import com.example.apiitem.item.adaptor.out.jpa.CategoryEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Category {
    private Integer id;
    private String category;

    public CategoryEntity toEntity() {
        return CategoryEntity.builder()
                .category(category)
                .build();
    }

    public static Category toDomain(CategoryEntity itemEntity) {
        return Category.builder()
                .id(itemEntity.getId())
                .category(itemEntity.getCategory())
                .build();
    }
}
