package com.example.apiitem.item.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemDetail {
    private Integer id;
    private Integer colorId;
    private String colorName;
    private String colorCode;
    private Integer sizeId;
    private String size;
    private String itemDetailCode; // 아이템 코드 + 색상 + 사이즈
    private Integer stock; // 재고
    private Integer itemId;
}
