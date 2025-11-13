package com.example.apiitem.item.usecase.port.in.request;

import lombok.Getter;

@Getter
public class UpdateItemKoNameCommand {
    private Integer itemId;
    private String koName;
}
