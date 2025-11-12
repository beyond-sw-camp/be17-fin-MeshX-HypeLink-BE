package com.example.apiitem.item.domain;

import MeshX.common.Domain;
import lombok.Builder;
import lombok.Getter;

@Getter
@Domain
@Builder
public class Color {
    private Integer id;
    private String colorName;
    private String colorCode;

}
