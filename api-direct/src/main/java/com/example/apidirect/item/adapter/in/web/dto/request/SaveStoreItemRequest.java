package com.example.apidirect.item.adapter.in.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveStoreItemRequest {
    private String category;
    private String itemCode;
    private Integer unitPrice;
    private Integer amount;
    private String enName;
    private String koName;
    private String content;
    private String company;
    private List<SaveStoreItemImageRequest> images;
    private List<SaveStoreItemDetailRequest> itemDetails;
}
