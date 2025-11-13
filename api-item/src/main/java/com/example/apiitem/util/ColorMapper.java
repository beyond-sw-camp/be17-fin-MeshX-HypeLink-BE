package com.example.apiitem.util;

import com.example.apiitem.item.adaptor.out.jpa.ColorEntity;
import com.example.apiitem.item.domain.Color;
import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaColor;
import com.example.apiitem.item.usecase.port.out.response.ColorInfoDto;
import com.example.apiitem.item.usecase.port.out.response.ColorInfoListDto;

import java.util.List;

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

    public static Color toDomain(ColorEntity entity) {
        return Color.builder()
                .id(entity.getId())
                .colorCode(entity.getColorCode())
                .colorName(entity.getColorName())
                .build();
    }

    public static ColorInfoListDto toDto(List<Color> entities) {
        List<ColorInfoDto> infoDtos = entities.stream()
                .map(one -> ColorInfoDto.builder()
                            .colorCode(one.getColorCode())
                            .colorName(one.getColorName())
                            .build())
                .toList();

        return ColorInfoListDto.builder()
                .colors(infoDtos)
                .build();
    }
}
