package com.example.apiauth.adapter.out.persistence.read.mapper;

import com.example.apiauth.adapter.out.persistence.read.entity.PosReadEntity;
import com.example.apiauth.domain.model.Pos;

public class PosReadMapper {

    public static Pos toDomain(PosReadEntity entity) {
        return Pos.builder()
                .id(entity.getId())
                .posCode(entity.getPosCode())
                .store(entity.getStore() != null ? StoreReadMapper.toDomain(entity.getStore()) : null)
                .member(entity.getMember() != null ? MemberReadMapper.toDomain(entity.getMember()) : null)
                .healthCheck(entity.getHealthCheck())
                .syncStatus(entity.getSyncStatus())
                .build();
    }

    public static PosReadEntity toEntity(Pos domain) {
        return PosReadEntity.builder()
                .id(domain.getId())
                .posCode(domain.getPosCode())
                .store(domain.getStore() != null ? StoreReadMapper.toEntity(domain.getStore()) : null)
                .member(domain.getMember() != null ? MemberReadMapper.toEntity(domain.getMember()) : null)
                .healthCheck(domain.getHealthCheck())
                .syncStatus(domain.getSyncStatus())
                .build();
    }
}
