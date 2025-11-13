package com.example.apiauth.adapter.in.web.dto;

import com.example.apiauth.domain.model.value.StoreState;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreListResDto {
    private Integer storeId;
    private String storeName;
    private String storeAddress;
    private String storePhone;
    private StoreState storeState;
}
