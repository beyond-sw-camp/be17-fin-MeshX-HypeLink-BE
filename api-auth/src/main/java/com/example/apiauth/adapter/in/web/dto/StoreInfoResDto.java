package com.example.apiauth.adapter.in.web.dto;

import com.example.apiauth.domain.model.value.Region;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreInfoResDto {
    private String name;
    private String email;
    private String phone;
    private String address; // 매장주소
    private Region region;
    private String storeNumber;

}