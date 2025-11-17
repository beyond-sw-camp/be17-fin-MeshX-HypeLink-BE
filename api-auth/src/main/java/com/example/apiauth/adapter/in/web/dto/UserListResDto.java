package com.example.apiauth.adapter.in.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserListResDto {
    private List<MemberListNotPosResDto> users;
}
