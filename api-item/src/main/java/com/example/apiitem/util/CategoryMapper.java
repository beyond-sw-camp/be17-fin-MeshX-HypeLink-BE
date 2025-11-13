package com.example.apiitem.util;

import com.example.apiitem.item.adaptor.out.jpa.CategoryEntity;
import com.example.apiitem.item.domain.Category;
import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaCategory;
import com.example.apiitem.item.usecase.port.out.response.CategoryInfoDto;
import com.example.apiitem.item.usecase.port.out.response.CategoryInfoListDto;

import java.util.List;

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

    public static CategoryInfoListDto toDto(List<Category> entities) {
        List<CategoryInfoDto> infoDtos = entities.stream()
                .map(one -> CategoryInfoDto.builder()
                        .category(one.getCategory())
                        .build())
                .toList();

        return CategoryInfoListDto.builder()
                .categories(infoDtos)
                .build();
    }
}
