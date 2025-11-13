package org.example.apidirect.item.adapter.in.web.dto.response;

import lombok.Builder;
import lombok.Getter;

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
}
