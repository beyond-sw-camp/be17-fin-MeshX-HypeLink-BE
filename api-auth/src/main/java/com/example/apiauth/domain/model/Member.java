package com.example.apiauth.domain.model;

import lombok.Builder;
import lombok.Getter;
import com.example.apiauth.adapter.out.persistence.entity.MemberRole;
import com.example.apiauth.adapter.out.persistence.entity.Region;

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



}
