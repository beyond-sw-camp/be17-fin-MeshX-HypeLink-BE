package com.example.apiitem.item.domain;

import lombok.Builder;
import lombok.Getter;

import java.awt.*;
import java.awt.print.Pageable;
import java.util.List;

@Getter
@Builder
public class Item {
    private Integer id;
    private String category;
    private List<ItemDetail> itemDetails;
    private List<ItemImage> itemImages;
    private String itemCode; // 아이템 코드
    private Integer unitPrice;       // 단가
    private Integer amount; // 가격
    private String enName; // 이름
    private String koName; // 이름
    private String content; // 아이템 설명
    private String company; // 회사
}
