package com.example.apiitem.item.usecase.port.out.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ColorInfoDto {
    private String colorName;
    private String colorCode;
}
