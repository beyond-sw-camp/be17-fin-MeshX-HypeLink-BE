package com.example.apidirect.payment.usecase.port.in.request;

import lombok.Getter;


@Getter
public class ReceiptItemCommand {
    private Integer storeItemDetailId;  // StoreItemDetail ID (색상/사이즈 포함)
    private String productName;
    private Integer quantity;
    private Integer unitPrice;
    private Integer subtotal;
}
