package com.example.apidirect.item.adapter.in.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveStoreItemDetailRequest {
    private Integer id; // 모놀리식 store_item_detail.id
    private String size;
    private String color;
    private String colorCode;
    private Integer stock;
    private String itemDetailCode;
}
