package com.example.apiauth.adapter.out.external.monolith.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosSyncDto {
    private Integer id;
    private String posCode;
    private Integer storeId;
    private Boolean healthCheck;
    private Integer memberId;
}
