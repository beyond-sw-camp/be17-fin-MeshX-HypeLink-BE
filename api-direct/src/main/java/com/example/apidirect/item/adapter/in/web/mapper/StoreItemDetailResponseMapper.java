package com.example.apidirect.item.adapter.in.web.mapper;

import com.example.apidirect.item.adapter.in.web.dto.response.StoreItemDetailResponse;
import com.example.apidirect.item.domain.StoreItemDetail;

public class StoreItemDetailResponseMapper {

    public static StoreItemDetailResponse toResponse(StoreItemDetail detail, String koName, String category, Integer price) {
        String productName = koName + " " + detail.getColorName() + "/" + detail.getSizeName();

        return StoreItemDetailResponse.builder()
                .id(detail.getId())
                .storeItemId(detail.getStoreItemId())
                .itemDetailCode(detail.getItemDetailCode())
                .productName(productName)
                .color(detail.getColorName())
                .colorCode(detail.getColorCode())
                .size(detail.getSizeName())
                .stock(detail.getStock())
                .price(price)
                .category(category)
                .itemCode(detail.getItemCode())
                .build();
    }
}
