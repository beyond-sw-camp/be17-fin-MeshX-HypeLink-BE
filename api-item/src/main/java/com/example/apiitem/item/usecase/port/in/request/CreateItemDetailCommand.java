package com.example.apiitem.item.usecase.port.in.request;

import lombok.Getter;

@Getter
public class CreateItemDetailCommand {
    private String size;
    private String color;
    private Integer stock; // 재고
    private String itemDetailCode; // 아이템 코드
}
