package com.example.apidirect.item.adapter.out.mapper;

import com.example.apidirect.item.adapter.out.entity.StoreItemDetailEntity;
import com.example.apidirect.item.adapter.out.entity.StoreItemEntity;
import com.example.apidirect.item.domain.StoreItemDetail;

public class ItemDetailMapper {

    public static StoreItemDetail toDomain(StoreItemDetailEntity entity) {
        if (entity == null) return null;

        StoreItemEntity item = entity.getItem();

        return StoreItemDetail.builder()
                .id(entity.getId())
                .itemDetailCode(entity.getItemDetailCode())
                .itemCode(item != null ? item.getItemCode() : null)
                .storeItemId(item != null ? item.getId() : null)
                .storeId(item != null ? item.getStore().getId() : null)
                .itemName(item != null ? item.getKoName() : null)
                .colorName(entity.getColor())
                .colorCode(entity.getColorCode())
                .sizeName(entity.getSize())
                .stock(entity.getStock())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                // StoreItem 정보 추가
                .koName(item != null ? item.getKoName() : null)
                .category(item != null && item.getCategory() != null ? item.getCategory().getCategory() : null)
                .amount(item != null ? item.getAmount() : null)
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

    /**
     * 기존 Entity를 업데이트 (재고 차감용)
     */
    public static void updateEntity(StoreItemDetailEntity entity, StoreItemDetail domain) {
        if (entity == null || domain == null) return;
        entity.updateStock(domain.getStock() - entity.getStock());
    }
}
