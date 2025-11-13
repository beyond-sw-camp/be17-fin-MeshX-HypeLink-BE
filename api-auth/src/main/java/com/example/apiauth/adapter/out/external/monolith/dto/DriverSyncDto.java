package com.example.apiauth.adapter.out.external.monolith.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverSyncDto {
    private Integer id;
    private String macAddress;
    private String carNumber;
    private Integer memberId;
}
