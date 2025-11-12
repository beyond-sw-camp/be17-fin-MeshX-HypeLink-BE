package com.example.apiitem.item.usecase.port.in.request;

import lombok.Getter;

@Getter
public class UpdateItemAmountCommand {
    private Integer itemId;
    private Integer amount;
}
