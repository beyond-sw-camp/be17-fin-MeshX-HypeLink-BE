package com.example.apiauth.adapter.out.persistence.mapper;

import com.example.apiauth.adapter.out.persistence.entity.StoreEntity;
import com.example.apiauth.domain.model.Store;

public class StoreMapper {

    private StoreMapper() {
    }

    public static StoreEntity toEntity(Store store) {
        return StoreEntity.builder()
                .id(store.getId())
                .member(MemberMapper.toEntity(store.getMember()))
                .lat(store.getLat())
                .lon(store.getLon())
                .posCount(store.getPosCount())
                .storeNumber(store.getStoreNumber())
                .storeState(store.getStoreState())
                .syncStatus(store.getSyncStatus())
                .build();
    }

    public static Store toDomain(StoreEntity storeEntity) {
        return Store.builder()
                .id(storeEntity.getId())
                .member(MemberMapper.toDomain(storeEntity.getMember()))
                .lat(storeEntity.getLat())
                .lon(storeEntity.getLon())
                .posCount(storeEntity.getPosCount())
                .storeNumber(storeEntity.getStoreNumber())
                .storeState(storeEntity.getStoreState())
                .syncStatus(storeEntity.getSyncStatus())
                .build();
    }

}
