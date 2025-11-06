package com.example.apiitem.item.domain;

import com.example.apiitem.item.adaptor.out.jpa.CategoryEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Category {
    private Integer id;
    private String category;
}
