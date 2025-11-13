package com.example.apiitem.item.usecase.port.out.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemUpdateInfoDto {
    private Integer id;
    private Integer amount; // 가격
    private String name; // 이름
    private String company; // 회사
    private String itemCode; // 아이템 코드
    private String stock;
    private String size;
    private String color;

    @Builder
    private ItemUpdateInfoDto(Integer id, Integer amount, String name, String company,
                              String itemCode, String stock, String size, String color) {
        this.id = id;
        this.amount = amount;
        this.name = name;
        this.company = company;
        this.itemCode = itemCode;
        this.stock = stock;
        this.size = size;
        this.color = color;
    }
}
