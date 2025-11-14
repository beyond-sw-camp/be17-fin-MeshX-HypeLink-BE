package org.example.apidirect.auth.adapter.out.mapper;

import org.example.apidirect.auth.adapter.out.entity.StoreEntity;
import org.example.apidirect.auth.domain.Store;

public class StoreMapper {

    public static Store toDomain(StoreEntity entity) {
        if (entity == null) return null;

        return Store.builder()
                .id(entity.getId())
                .lat(entity.getLat())
                .lon(entity.getLon())
                .posCount(entity.getPosCount())
                .storeNumber(entity.getStoreNumber())
                .storeState(entity.getStoreState())
                .memberId(entity.getMemberId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static StoreEntity toEntity(Store domain) {
        if (domain == null) return null;

        return StoreEntity.builder()
                .id(domain.getId())
                .lat(domain.getLat())
                .lon(domain.getLon())
                .posCount(domain.getPosCount())
                .storeNumber(domain.getStoreNumber())
                .storeState(domain.getStoreState())
                .memberId(domain.getMemberId())
                .build();
    }
}
