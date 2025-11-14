package com.example.apiauth.domain.model;

import com.example.apiauth.domain.model.value.MemberRole;
import com.example.apiauth.domain.model.value.Region;
import com.example.apiauth.domain.model.value.SyncStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Member {

    private Integer id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;
    private MemberRole role;
    private Region region;
    private String refreshToken;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private SyncStatus syncStatus;

    public static Member createNew(String email, String password, String name,
                                   String phone, String address, MemberRole role, Region region) {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .address(address)
                .role(role)
                .region(region)
                .refreshToken(null)
                .syncStatus(SyncStatus.NEW)
                .build();
    }

    /**
     * POS 회원 생성 (Store 회원의 정보를 상속)
     */
    public static Member createPosFromStore(String email, String encodedPassword, String name, Member storeMember) {
        return Member.builder()
                .email(email)
                .password(encodedPassword)
                .name(name)
                .phone(storeMember.getPhone())
                .address(storeMember.getAddress())
                .role(MemberRole.POS_MEMBER)
                .region(storeMember.getRegion())
                .refreshToken(null)
                .syncStatus(SyncStatus.NEW)
                .build();
    }

}