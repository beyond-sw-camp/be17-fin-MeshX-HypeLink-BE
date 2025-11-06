package com.example.apiauth.adapter.out.persistence.mapper;

import com.example.apiauth.adapter.out.persistence.entity.PosEntity;
import com.example.apiauth.domain.model.Pos;

public class PosMapper {

    private PosMapper() {}

    public static PosEntity toEntity(Pos pos){
        return PosEntity.builder()
                .id(pos.getId())
                .member(MemberMapper.toEntity(pos.getMember()))
                .store(StoreMapper.toEntity(pos.getStore()))
                .posCode(pos.getPosCode())
                .healthCheck(pos.getHealthCheck())
                .build();
    }

    public static Pos toDomain(PosEntity posEntity){
        return Pos.builder()
                .id(posEntity.getId())
                .member(MemberMapper.toDomain(posEntity.getMember()))
                .store(StoreMapper.toDomain(posEntity.getStore()))
                .posCode(posEntity.getPosCode())
                .healthCheck(posEntity.getHealthCheck())
                .build();
    }

}
