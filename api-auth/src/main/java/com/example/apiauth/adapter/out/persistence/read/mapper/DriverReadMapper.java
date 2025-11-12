package com.example.apiauth.adapter.out.persistence.read.mapper;

import com.example.apiauth.adapter.out.persistence.read.entity.DriverReadEntity;
import com.example.apiauth.domain.model.Driver;

public class DriverReadMapper {

    public static Driver toDomain(DriverReadEntity entity) {
        return Driver.builder()
                .id(entity.getId())
                .member(entity.getMember() != null ? MemberReadMapper.toDomain(entity.getMember()) : null)
                .macAddress(entity.getMacAddress())
                .carNumber(entity.getCarNumber())
                .build();
    }

    public static DriverReadEntity toEntity(Driver domain) {
        return DriverReadEntity.builder()
                .id(domain.getId())
                .member(domain.getMember() != null ? MemberReadMapper.toEntity(domain.getMember()) : null)
                .macAddress(domain.getMacAddress())
                .carNumber(domain.getCarNumber())
                .build();
    }
}
