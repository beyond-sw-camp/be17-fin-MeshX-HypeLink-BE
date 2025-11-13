package org.example.apidirect.item.adapter.in.web.mapper;

import org.example.apidirect.item.adapter.in.web.dto.response.StoreItemDetailResponse;
import org.example.apidirect.item.domain.StoreItemDetail;

public class StoreItemDetailResponseMapper {

    public static StoreItemDetailResponse toResponse(StoreItemDetail detail, String koName, String category, Integer price) {
        String productName = koName + " " + detail.getColor() + "/" + detail.getSize();

        return StoreItemDetailResponse.builder()
                .id(detail.getId())
                .storeItemId(detail.getStoreItemId())
                .itemDetailCode(detail.getItemDetailCode())
                .productName(productName)
                .color(detail.getColor())
                .colorCode(detail.getColorCode())
                .size(detail.getSize())
                .stock(detail.getStock())
                .price(price)
                .category(category)
                .itemCode(detail.getItemCode())
                .build();
    }
}
