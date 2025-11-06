package com.example.apiauth.adapter.out.persistence.mapper;

import com.example.apiauth.adapter.out.persistence.entity.DriverEntity;
import com.example.apiauth.domain.model.Driver;

public class DriverMapper {

    private DriverMapper() {}

    public static DriverEntity toEntity(Driver driver) {
        return DriverEntity.builder()
                .id(driver.getId())
                .member(MemberMapper.toEntity(driver.getMember()))
                .carNumber(driver.getCarNumber())
                .macAddress(driver.getMacAddress())
                .build();
    }
    public static Driver toDomain(DriverEntity driverEntity) {
        return Driver.builder()
                .id(driverEntity.getId())
                .member(MemberMapper.toDomain(driverEntity.getMember()))
                .carNumber(driverEntity.getCarNumber())
                .macAddress(driverEntity.getMacAddress())
                .build();
    }
}
