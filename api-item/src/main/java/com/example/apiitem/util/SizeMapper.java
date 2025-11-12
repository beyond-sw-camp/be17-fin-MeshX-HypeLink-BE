package com.example.apiitem.util;

import com.example.apiitem.item.adaptor.out.jpa.SizeEntity;
import com.example.apiitem.item.domain.Size;
import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaSize;

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
}
