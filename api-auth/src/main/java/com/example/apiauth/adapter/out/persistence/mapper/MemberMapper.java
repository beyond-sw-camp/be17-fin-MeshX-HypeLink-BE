package com.example.apiauth.adapter.out.persistence.mapper;

import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.domain.model.Member;

public class MemberMapper {

    private MemberMapper(){};

    public static Member toDomain(MemberEntity memberEntity){
        return Member.builder()
                .id(memberEntity.getId())
                .email(memberEntity.getEmail())
                .password(memberEntity.getPassword())
                .name(memberEntity.getName())
                .phone(memberEntity.getPhone())
                .address(memberEntity.getAddress())
                .role(memberEntity.getRole())
                .region(memberEntity.getRegion())
                .refreshToken(memberEntity.getRefreshToken())
                .build();
    }

    public static MemberEntity toEntity(Member member){
        return MemberEntity.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .phone(member.getPhone())
                .address(member.getAddress())
                .role(member.getRole())
                .region(member.getRegion())
                .refreshToken(member.getRefreshToken())
                .build();
    }
}
