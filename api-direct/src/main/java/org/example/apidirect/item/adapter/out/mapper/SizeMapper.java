package org.example.apidirect.item.adapter.out.mapper;

import org.example.apidirect.item.adapter.out.entity.SizeEntity;
import org.example.apidirect.item.domain.Size;

public class SizeMapper {

    public static Size toDomain(SizeEntity entity) {
        if (entity == null) return null;

        return Size.builder()
                .id(entity.getId())
                .size(entity.getSize())
                .build();
    }

    public static SizeEntity toEntity(Size domain) {
        if (domain == null) return null;

        return SizeEntity.builder()
                .id(domain.getId())
                .size(domain.getSize())
                .build();
    }
}
