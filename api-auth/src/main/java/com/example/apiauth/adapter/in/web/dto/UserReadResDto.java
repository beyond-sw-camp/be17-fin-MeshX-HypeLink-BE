package com.example.apiauth.adapter.in.web.dto;

import com.example.apiauth.domain.model.value.MemberRole;
import com.example.apiauth.domain.model.value.Region;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserReadResDto {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Region region;
    private MemberRole role;
    private Integer storeId;
    private String storeNumber;// 가게 전화 번호
    private Integer driverId;
    private String carNumber;
    private String macAddress;

}
