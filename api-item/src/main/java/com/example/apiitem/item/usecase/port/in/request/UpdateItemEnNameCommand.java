package com.example.apiitem.item.usecase.port.in.request;

import lombok.Getter;

@Getter
public class UpdateItemEnNameCommand {
    private Integer itemId;
    private String enName;
}
