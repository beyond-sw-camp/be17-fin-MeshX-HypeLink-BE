package org.example.apidirect.item.adapter.in.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.example.apidirect.item.domain.StoreItemDetail;

@Getter
@Builder
public class StoreItemDetailResponse {
    private Integer id;
    private Integer storeItemId;
    private String itemDetailCode;
    private String productName;
    private String color;
    private String colorCode;
    private String size;
    private Integer stock;
    private Integer price;
    private String category;
    private String itemCode;

    public static StoreItemDetailResponse from(StoreItemDetail detail, String koName, String category, Integer price) {
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
