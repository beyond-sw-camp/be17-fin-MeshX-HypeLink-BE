package org.example.apidirect.item.adapter.out.mapper;

import org.example.apidirect.item.adapter.out.entity.ColorEntity;
import org.example.apidirect.item.adapter.out.entity.SizeEntity;
import org.example.apidirect.item.adapter.out.entity.StoreItemDetailEntity;
import org.example.apidirect.item.adapter.out.entity.StoreItemEntity;
import org.example.apidirect.item.domain.StoreItemDetail;

public class ItemDetailMapper {

    public static StoreItemDetail toDomain(StoreItemDetailEntity entity) {
        if (entity == null) return null;

        return StoreItemDetail.builder()
                .id(entity.getId())
                .itemDetailCode(entity.getItemDetailCode())
                .itemCode(entity.getItem() != null ? entity.getItem().getItemCode() : null)
                .storeItemId(entity.getItem() != null ? entity.getItem().getId() : null)
                .colorId(entity.getColor() != null ? entity.getColor().getId() : null)
                .sizeId(entity.getSize() != null ? entity.getSize().getId() : null)
                .color(entity.getColor() != null ? entity.getColor().getColor() : null)
                .colorCode(entity.getColor() != null ? entity.getColor().getColorCode() : null)
                .size(entity.getSize() != null ? entity.getSize().getSize() : null)
                .stock(entity.getStock())
                .build();
    }

    public static StoreItemDetailEntity toEntity(StoreItemDetail domain, ColorEntity color, SizeEntity size, StoreItemEntity item) {
        if (domain == null) return null;

        return StoreItemDetailEntity.builder()
                .itemDetailCode(domain.getItemDetailCode())
                .color(color)
                .size(size)
                .stock(domain.getStock())
                .item(item)
                .build();
    }
}
