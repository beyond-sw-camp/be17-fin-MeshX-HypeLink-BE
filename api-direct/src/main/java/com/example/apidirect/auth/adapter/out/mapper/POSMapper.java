package com.example.apidirect.auth.adapter.out.mapper;

import com.example.apidirect.auth.adapter.out.entity.POSEntity;
import com.example.apidirect.auth.domain.POS;

public class POSMapper {

    public static POS toDomain(POSEntity entity) {
        if (entity == null) return null;

        return POS.builder()
                .id(entity.getId())
                .posCode(entity.getPosCode())
                .storeId(entity.getStoreId())
                .healthCheck(entity.getHealthCheck())
                .memberId(entity.getMemberId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static POSEntity toEntity(POS domain) {
        if (domain == null) return null;

        return POSEntity.builder()
                .id(domain.getId())
                .posCode(domain.getPosCode())
                .storeId(domain.getStoreId())
                .healthCheck(domain.getHealthCheck())
                .memberId(domain.getMemberId())
                .build();
    }
}
