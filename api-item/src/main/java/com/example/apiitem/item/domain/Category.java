package com.example.apiitem.item.domain;

import MeshX.common.Domain;
import lombok.Builder;
import lombok.Getter;

@Getter
@Domain
@Builder
public class Category {
    private Integer id;
    private String category;
}
