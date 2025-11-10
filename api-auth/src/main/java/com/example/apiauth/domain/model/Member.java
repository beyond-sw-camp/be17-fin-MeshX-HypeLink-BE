package com.example.apiauth.domain.model;

import com.example.apiauth.domain.model.value.MemberRole;
import com.example.apiauth.domain.model.value.Region;
import lombok.Builder;
import lombok.Getter;

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