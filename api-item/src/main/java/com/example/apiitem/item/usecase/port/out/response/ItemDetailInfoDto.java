package com.example.apiitem.item.usecase.port.out.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemDetailInfoDto {
    private Integer id;
    private String itemDetailCode;
    private String color;
    private String colorCode;
    private String size;
    private Integer stock;
}
