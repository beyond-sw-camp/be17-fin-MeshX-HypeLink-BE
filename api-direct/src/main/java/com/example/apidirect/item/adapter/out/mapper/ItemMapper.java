package com.example.apidirect.item.adapter.out.mapper;

import com.example.apidirect.auth.adapter.out.entity.StoreEntity;
import com.example.apidirect.item.adapter.out.entity.StoreCategoryEntity;
import com.example.apidirect.item.adapter.out.entity.StoreItemEntity;
import com.example.apidirect.item.domain.StoreItem;

public class ItemMapper {

    public static StoreItem toDomain(StoreItemEntity entity) {
        if (entity == null) return null;

        return StoreItem.builder()
                .id(entity.getId())
                .itemCode(entity.getItemCode())
                .storeId(entity.getStore() != null ? entity.getStore().getId() : null)
                .unitPrice(entity.getUnitPrice())
                .amount(entity.getAmount())
                .enName(entity.getEnName())
                .koName(entity.getKoName())
                .content(entity.getContent())
                .company(entity.getCompany())
                .category(entity.getCategory() != null ? entity.getCategory().getCategory() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static StoreItemEntity toEntity(StoreItem domain, StoreEntity store, StoreCategoryEntity category) {
        if (domain == null) return null;

        return StoreItemEntity.builder()
                .id(domain.getId())
                .itemCode(domain.getItemCode())
                .store(store)
                .unitPrice(domain.getUnitPrice())
                .amount(domain.getAmount())
                .enName(domain.getEnName())
                .koName(domain.getKoName())
                .content(domain.getContent())
                .company(domain.getCompany())
                .category(category)
                .build();
    }
}
