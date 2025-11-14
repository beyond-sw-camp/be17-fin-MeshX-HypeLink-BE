package org.example.apidirect.item.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class StoreItem {
    private Integer id;
    private String itemCode;
    private Integer unitPrice;
    private Integer amount;
    private String enName;
    private String koName;
    private String content;
    private String company;
    private String category;

    private List<StoreItemDetail> itemDetails;
    private List<StoreItemImage> itemImages;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
