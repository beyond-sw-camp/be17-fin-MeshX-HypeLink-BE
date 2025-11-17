package com.example.apiauth.adapter.out.persistence.read.mapper;

import com.example.apiauth.adapter.out.persistence.read.entity.StoreReadEntity;
import com.example.apiauth.domain.model.Store;

public class StoreReadMapper {

    public static Store toDomain(StoreReadEntity entity) {
        return Store.builder()
                .id(entity.getId())
                .member(entity.getMember() != null ? MemberReadMapper.toDomain(entity.getMember()) : null)
                .lat(entity.getLat())
                .lon(entity.getLon())
                .posCount(entity.getPosCount())
                .storeNumber(entity.getStoreNumber())
                .storeState(entity.getStoreState())
                .syncStatus(entity.getSyncStatus())
                .build();
    }

    public static StoreReadEntity toEntity(Store domain) {
        return StoreReadEntity.builder()
                .id(domain.getId())
                .member(domain.getMember() != null ? MemberReadMapper.toEntity(domain.getMember()) : null)
                .lat(domain.getLat())
                .lon(domain.getLon())
                .posCount(domain.getPosCount())
                .storeNumber(domain.getStoreNumber())
                .storeState(domain.getStoreState())
                .syncStatus(domain.getSyncStatus())
                .build();
    }
}
