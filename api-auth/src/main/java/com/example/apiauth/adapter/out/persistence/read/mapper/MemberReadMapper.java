package com.example.apiauth.adapter.out.persistence.read.mapper;

import com.example.apiauth.adapter.out.persistence.read.entity.MemberReadEntity;
import com.example.apiauth.domain.model.Member;

public class MemberReadMapper {

    public static Member toDomain(MemberReadEntity entity) {
        return Member.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .name(entity.getName())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .role(entity.getRole())
                .region(entity.getRegion())
                .refreshToken(entity.getRefreshToken())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static MemberReadEntity toEntity(Member domain) {
        return MemberReadEntity.builder()
                .id(domain.getId())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .name(domain.getName())
                .phone(domain.getPhone())
                .address(domain.getAddress())
                .role(domain.getRole())
                .region(domain.getRegion())
                .refreshToken(domain.getRefreshToken())
                .build();
    }
}
