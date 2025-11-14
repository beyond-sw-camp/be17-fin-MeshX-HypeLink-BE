package com.example.apidirect.item.adapter.out.mapper;

import com.example.apidirect.item.adapter.out.entity.StoreItemDetailEntity;
import com.example.apidirect.item.adapter.out.entity.StoreItemEntity;
import com.example.apidirect.item.domain.StoreItemDetail;

public class ItemDetailMapper {

    public static StoreItemDetail toDomain(StoreItemDetailEntity entity) {
        if (entity == null) return null;

        return StoreItemDetail.builder()
                .id(entity.getId())
                .itemDetailCode(entity.getItemDetailCode())
                .itemCode(entity.getItem() != null ? entity.getItem().getItemCode() : null)
                .storeItemId(entity.getItem() != null ? entity.getItem().getId() : null)
                .itemName(entity.getItem() != null ? entity.getItem().getKoName() : null)
                .colorName(entity.getColor())
                .colorCode(entity.getColorCode())
                .sizeName(entity.getSize())
                .stock(entity.getStock())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static StoreItemDetailEntity toEntity(StoreItemDetail domain, StoreItemEntity item) {
        if (domain == null) return null;

        return StoreItemDetailEntity.builder()
                .color(domain.getColorName())
                .colorCode(domain.getColorCode())
                .size(domain.getSizeName())
                .stock(domain.getStock())
                .itemDetailCode(domain.getItemDetailCode())
                .item(item)
                .build();
    }
}
