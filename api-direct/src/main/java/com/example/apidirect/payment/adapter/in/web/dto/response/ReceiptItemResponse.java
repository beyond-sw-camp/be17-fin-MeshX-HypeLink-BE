package com.example.apidirect.payment.adapter.in.web.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReceiptItemResponse {
    private Integer id;
    private Integer storeItemDetailId;
    private String productName;
    private String color;
    private String size;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
}
