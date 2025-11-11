package com.example.apiauth.adapter.in.web.dto;

import com.example.apiauth.domain.model.value.MemberRole;
import com.example.apiauth.domain.model.value.Region;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberListNotPosResDto {
    private Integer id;
    private String name;
    private String email;
    private MemberRole role;
    private Region region;
}
