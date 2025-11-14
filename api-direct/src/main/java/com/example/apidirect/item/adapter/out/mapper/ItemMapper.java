package com.example.apidirect.item.adapter.out.mapper;

import com.example.apidirect.item.adapter.out.entity.StoreItemEntity;
import com.example.apidirect.item.domain.StoreItem;

public class ItemMapper {

    public static StoreItem toDomain(StoreItemEntity entity) {
        if (entity == null) return null;

        return StoreItem.builder()
                .id(entity.getId())
                .itemCode(entity.getItemCode())
                .storeId(entity.getStoreId())
                .unitPrice(entity.getUnitPrice())
                .amount(entity.getAmount())
                .enName(entity.getEnName())
                .koName(entity.getKoName())
                .content(entity.getContent())
                .company(entity.getCompany())
                .category(entity.getCategory())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static StoreItemEntity toEntity(StoreItem domain) {
        if (domain == null) return null;

        return StoreItemEntity.builder()
                .id(domain.getId())
                .itemCode(domain.getItemCode())
                .storeId(domain.getStoreId())
                .unitPrice(domain.getUnitPrice())
                .amount(domain.getAmount())
                .enName(domain.getEnName())
                .koName(domain.getKoName())
                .content(domain.getContent())
                .company(domain.getCompany())
                .category(domain.getCategory())
                .build();
    }
}
