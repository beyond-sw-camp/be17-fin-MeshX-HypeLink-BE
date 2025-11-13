package com.example.apiitem.item.usecase.port.out.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ItemInfoDto {
    private Integer id;
    private String itemCode; // 아이템 코드
    private String enName;
    private String koName;
    private String category;
    private Integer amount; // 가격
    private Integer unitPrice; // 원가
    private String content; // 아이템 설명
    private String company; // 회사
    private List<ItemImageInfoDto> images;
}
