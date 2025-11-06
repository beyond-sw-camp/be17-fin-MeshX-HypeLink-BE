package com.example.apiitem.item.usecase.port.out.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemAndItemDetailInfoDto {
    private Integer id;
    private String category;
    private String color;
    private String colorCode;
    private String size;
    private Integer amount; // 가격
    private Integer unitPrice; // 원가
    private String enName; // 이름
    private String koName; // 이름
    private String content; // 아이템 설명
    private String company; // 회사
    private String itemCode; // 아이템 코드
    private String itemDetailCode; // 아이템 코드 + 색상 + 사이즈
    private Integer stock; // 재고
}
