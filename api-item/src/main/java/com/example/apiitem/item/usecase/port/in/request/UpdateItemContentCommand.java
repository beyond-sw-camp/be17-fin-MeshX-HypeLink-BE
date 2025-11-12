package com.example.apiitem.item.usecase.port.in.request;

import lombok.Getter;

@Getter
public class UpdateItemContentCommand {
    private Integer itemId;
    private String content;
}
