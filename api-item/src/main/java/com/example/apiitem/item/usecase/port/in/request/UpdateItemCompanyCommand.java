package com.example.apiitem.item.usecase.port.in.request;

import lombok.Getter;

@Getter
public class UpdateItemCompanyCommand {
    private Integer itemId;
    private String company;
}
