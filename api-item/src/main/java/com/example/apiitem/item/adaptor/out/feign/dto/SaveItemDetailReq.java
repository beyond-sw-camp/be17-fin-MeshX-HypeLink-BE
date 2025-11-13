package com.example.apiitem.item.adaptor.out.feign.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveItemDetailReq {
    private Integer id;
    private String size;
    private String color;
    private Integer stock; // 재고
    private String itemDetailCode; // 아이템 코드
}
