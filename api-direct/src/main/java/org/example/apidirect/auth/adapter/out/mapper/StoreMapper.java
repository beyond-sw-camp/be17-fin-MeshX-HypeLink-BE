package org.example.apidirect.auth.adapter.out.mapper;

import org.example.apidirect.auth.adapter.out.entity.StoreEntity;
import org.example.apidirect.auth.domain.Store;

public class StoreMapper {

    public static Store toDomain(StoreEntity entity) {
        if (entity == null) return null;

        return Store.builder()
                .id(entity.getId())
                .storeName(entity.getStoreName())
                .storeCode(entity.getStoreCode())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .businessNumber(entity.getBusinessNumber())
                .build();
    }

    public static StoreEntity toEntity(Store domain) {
        if (domain == null) return null;

        return StoreEntity.builder()
                .id(domain.getId())
                .storeName(domain.getStoreName())
                .storeCode(domain.getStoreCode())
                .address(domain.getAddress())
                .phone(domain.getPhone())
                .businessNumber(domain.getBusinessNumber())
                .build();
    }
}
