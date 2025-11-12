package com.example.apiauth.adapter.out.external.monolith.dto;

import com.example.apiauth.domain.model.value.MemberRole;
import com.example.apiauth.domain.model.value.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSyncDto {
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
}
