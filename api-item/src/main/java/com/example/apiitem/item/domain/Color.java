package com.example.apiitem.item.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Color {
    private Integer id;
    private String colorName;
    private String colorCode;

}
