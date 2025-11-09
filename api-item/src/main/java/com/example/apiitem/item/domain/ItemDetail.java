package com.example.apiitem.item.domain;

import MeshX.common.Domain;
import lombok.Builder;
import lombok.Getter;

@Getter
@Domain
@Builder
public class ItemDetail {
    private Integer id;
    private Integer categoryId;
    private String category;
    private Integer amount;
    private Integer unitPrice;
    private String enName;
    private String koName;
    private String content;
    private String company;
    private Integer itemId;
    private String itemCode;

    private Integer colorId;
    private String colorName;
    private String colorCode;
    private Integer sizeId;
    private String size;
    private String itemDetailCode; // 아이템 코드 + 색상 + 사이즈
    private Integer stock; // 재고
}
