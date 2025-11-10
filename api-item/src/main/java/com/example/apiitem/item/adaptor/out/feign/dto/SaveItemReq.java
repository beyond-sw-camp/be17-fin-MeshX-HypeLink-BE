package com.example.apiitem.item.adaptor.out.feign.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SaveItemReq {
    private Integer id;
    private String enName;      // 이름
    private String koName;      // 이름
    private Integer amount;     // 가격
    private Integer unitPrice;  // 원가
    private String category;
    private String itemCode;
    private String content;     // 아이템 설명
    private String company;     // 회사
    private List<SaveItemDetailReq> itemDetailList;
}
