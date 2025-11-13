package com.example.apiitem.util;

import com.example.apiitem.item.adaptor.out.jpa.SizeEntity;
import com.example.apiitem.item.domain.Size;
import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaSize;
import com.example.apiitem.item.usecase.port.out.response.SizeInfoDto;
import com.example.apiitem.item.usecase.port.out.response.SizeInfoListDto;

import java.util.List;

public class SizeMapper {
    public static SizeEntity toEntity(Size domain) {
        return SizeEntity.builder()
                .id(domain.getId())
                .size(domain.getSize())
                .build();
    }

    public static Size toDomain(KafkaSize dto) {
        return Size.builder()
                .id(dto.getId())
                .size(dto.getSize())
                .build();
    }

    public static Size toDomain(SizeEntity entity) {
        return Size.builder()
                .id(entity.getId())
                .size(entity.getSize())
                .build();
    }

    public static SizeInfoListDto toDto(List<Size> entities) {
        List<SizeInfoDto> infoDtos = entities.stream()
                .map(one -> SizeInfoDto.builder()
                        .size(one.getSize())
                        .build())
                .toList();

        return SizeInfoListDto.builder()
                .sizes(infoDtos)
                .build();
    }
}
