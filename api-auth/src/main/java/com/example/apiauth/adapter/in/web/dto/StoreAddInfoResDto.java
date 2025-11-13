package com.example.apiauth.adapter.in.web.dto;

import com.example.apiauth.domain.model.Store;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreAddInfoResDto {
    private Integer id;
    private String storeName;
    private Double lat;
    private Double lon;

    public static StoreAddInfoResDto toDto(Store store) {
        return StoreAddInfoResDto.builder()
                .id(store.getId())
                .lat(store.getLat())
                .lon(store.getLon())
                .storeName(store.getMember().getName())
                .build();
    }

}

