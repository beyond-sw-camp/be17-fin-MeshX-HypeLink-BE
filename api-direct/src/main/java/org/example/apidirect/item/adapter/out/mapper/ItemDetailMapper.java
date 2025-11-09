package org.example.apidirect.item.adapter.out.mapper;

import org.example.apidirect.item.adapter.out.entity.StoreItemDetailEntity;
import org.example.apidirect.item.adapter.out.entity.StoreItemEntity;
import org.example.apidirect.item.domain.StoreItemDetail;

public class ItemDetailMapper {

    public static StoreItemDetail toDomain(StoreItemDetailEntity entity) {
        if (entity == null) return null;

        return StoreItemDetail.builder()
                .id(entity.getId())
                .itemDetailCode(entity.getItemDetailCode())
                .color(entity.getColor())
                .colorCode(entity.getColorCode())
                .size(entity.getSize())
                .stock(entity.getStock())
                .build();
    }

    public static StoreItemDetailEntity toEntity(StoreItemDetail domain, StoreItemEntity item) {
        if (domain == null) return null;

        return StoreItemDetailEntity.builder()
                .itemDetailCode(domain.getItemDetailCode())
                .color(domain.getColor())
                .colorCode(domain.getColorCode())
                .size(domain.getSize())
                .stock(domain.getStock())
                .item(item)
                .build();
    }
}
