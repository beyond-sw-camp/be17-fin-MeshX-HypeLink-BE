package org.example.apidirect.item.adapter.out.mapper;

import org.example.apidirect.item.adapter.out.entity.ColorEntity;
import org.example.apidirect.item.domain.Color;

public class ColorMapper {

    public static Color toDomain(ColorEntity entity) {
        if (entity == null) return null;

        return Color.builder()
                .id(entity.getId())
                .colorName(entity.getColorName())
                .colorCode(entity.getColorCode())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static ColorEntity toEntity(Color domain) {
        if (domain == null) return null;

        return ColorEntity.builder()
                .id(domain.getId())
                .colorName(domain.getColorName())
                .colorCode(domain.getColorCode())
                .build();
    }
}
