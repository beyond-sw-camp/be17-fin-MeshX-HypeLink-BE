package com.example.apiauth.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PosInfoDto {
    private Integer id;
    private String name;
    private String email;
    private String posCode;
}