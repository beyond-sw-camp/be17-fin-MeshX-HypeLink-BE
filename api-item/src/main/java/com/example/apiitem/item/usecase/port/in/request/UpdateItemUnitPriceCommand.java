package com.example.apiitem.item.usecase.port.in.request;

import lombok.Getter;

@Getter
public class UpdateItemUnitPriceCommand {
    private Integer itemId;
    private Integer unitPrice;
}
