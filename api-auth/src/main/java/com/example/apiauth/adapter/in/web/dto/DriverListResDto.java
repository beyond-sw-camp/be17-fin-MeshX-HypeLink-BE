package com.example.apiauth.adapter.in.web.dto;

import com.example.apiauth.domain.model.value.Region;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DriverListResDto {
    private Integer id;
    private String name;
    private String phone;
    private Region region;
    private String macAddress;
    private String carNumber;

}
