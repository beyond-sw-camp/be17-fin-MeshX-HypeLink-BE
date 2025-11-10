package com.example.apiitem.item.adaptor.out.feign.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SaveItemDetailsReq {
    private Integer itemId;
    private List<SaveItemDetailReq> details;
}
