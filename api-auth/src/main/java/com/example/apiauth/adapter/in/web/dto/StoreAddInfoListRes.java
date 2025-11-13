package com.example.apiauth.adapter.in.web.dto;

import com.example.apiauth.domain.model.Store;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StoreAddInfoListRes {
    private List<StoreAddInfoResDto> stores;

    public static StoreAddInfoListRes toDto(List<Store> stores) {
        return StoreAddInfoListRes.builder()
                .stores(stores.stream().map(StoreAddInfoResDto::toDto).toList())
                .build();
    }
}
