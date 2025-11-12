package com.example.apiitem.item.usecase.port.in.request;

import lombok.Getter;

@Getter
public class UpdateItemCategoryCommand {
    private Integer itemId;
    private String category;
}
