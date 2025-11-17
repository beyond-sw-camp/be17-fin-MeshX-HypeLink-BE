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
    private String size;
    private String color;
    private String colorCode;
    private Integer stock;
    private String itemDetailCode;
}
