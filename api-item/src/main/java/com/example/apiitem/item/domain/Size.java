package com.example.apiitem.item.domain;

import MeshX.common.Domain;
import lombok.Builder;
import lombok.Getter;

@Getter
@Domain
@Builder
public class Size {
    private Integer id;
    private String size;
}
