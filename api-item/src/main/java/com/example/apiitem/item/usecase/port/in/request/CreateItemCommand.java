package com.example.apiitem.item.usecase.port.in.request;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateItemCommand {
    private String enName;      // 이름
    private String koName;      // 이름
    private Integer amount;     // 가격
    private Integer unitPrice;  // 원가
    private String category;
    private String itemCode;
    private String content;     // 아이템 설명
    private String company;     // 회사
    private List<CreateItemDetailCommand> itemDetailList;
    private List<CreateItemImageCommand> itemImages;
}
