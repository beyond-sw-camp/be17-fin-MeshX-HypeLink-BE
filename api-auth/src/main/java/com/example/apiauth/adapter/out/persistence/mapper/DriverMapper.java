package com.example.apiauth.adapter.out.persistence.mapper;

import com.example.apiauth.adapter.out.persistence.entity.DriverEntity;
import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.domain.model.Driver;

public class DriverMapper {

    private DriverMapper() {}

    public static DriverEntity toEntity(Driver driver) {
        return DriverEntity.builder()
                .id(driver.getId())
                .member(MemberMapper.toEntity(driver.getMember()))
                .carNumber(driver.getCarNumber())
                .macAddress(driver.getMacAddress())
                .syncStatus(driver.getSyncStatus())
                .build();
    }

    // Member ID만 사용하는 toEntity (저장 시 사용)
    public static DriverEntity toEntityWithMemberId(Driver driver, MemberEntity memberEntity) {
        return DriverEntity.builder()
                .id(driver.getId())
                .member(memberEntity)  // 영속화된 MemberEntity 직접 설정
                .carNumber(driver.getCarNumber())
                .macAddress(driver.getMacAddress())
                .syncStatus(driver.getSyncStatus())
                .build();
    }

    public static Driver toDomain(DriverEntity driverEntity) {
        return Driver.builder()
                .id(driverEntity.getId())
                .member(MemberMapper.toDomain(driverEntity.getMember()))
                .carNumber(driverEntity.getCarNumber())
                .macAddress(driverEntity.getMacAddress())
                .syncStatus(driverEntity.getSyncStatus())
                .build();
    }
}
