package org.example.apidirect.item.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreItemDetail {
    private Integer id;
    private String itemDetailCode;
    private String color;
    private String colorCode;
    private String size;
    private Integer stock;
}
