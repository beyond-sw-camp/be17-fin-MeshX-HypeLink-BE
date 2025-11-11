package com.example.apiauth.adapter.in.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreAddInfoResDto {
    private Integer id;
    private String storeName;
    private Double lat;
    private Double lon;

}

