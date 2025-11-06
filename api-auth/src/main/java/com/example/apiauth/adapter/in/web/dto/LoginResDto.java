package com.example.apiauth.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import com.example.apiauth.domain.model.value.MemberRole;
import com.example.apiauth.domain.model.value.AuthTokens;

@Getter
public class LoginResDto {
    private String email;
    private String name;
    private MemberRole role;

    @JsonIgnore
    private AuthTokens authTokens;

    @Builder
    public LoginResDto(String email, String name, MemberRole role, AuthTokens authTokens) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.authTokens = authTokens;
    }

    public String getAccessToken() {
        return authTokens.getAccessToken();
    }
}
