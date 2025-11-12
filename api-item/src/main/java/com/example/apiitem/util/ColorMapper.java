package com.example.apiitem.util;

import com.example.apiitem.item.adaptor.out.jpa.ColorEntity;
import com.example.apiitem.item.domain.Color;
import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaColor;

public class ColorMapper {
    public static ColorEntity toEntity(Color domain) {
        return ColorEntity.builder()
                .id(domain.getId())
                .colorName(domain.getColorName())
                .colorCode(domain.getColorCode())
                .build();
    }

    public static Color toDomain(KafkaColor dto) {
        return Color.builder()
                .id(dto.getId())
                .colorCode(dto.getColorCode())
                .colorName(dto.getColorName())
                .build();
    }
}
