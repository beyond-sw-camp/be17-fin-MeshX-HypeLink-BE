package com.example.apidirect.payment.adapter.in.web.mapper;

import com.example.apidirect.payment.adapter.in.web.dto.response.ReceiptItemResponse;
import com.example.apidirect.payment.adapter.in.web.dto.response.ReceiptResponse;
import com.example.apidirect.payment.domain.CustomerReceipt;
import com.example.apidirect.payment.domain.OrderItem;

import java.util.List;

public class ReceiptResponseMapper {

    public static ReceiptResponse toResponse(CustomerReceipt receipt, List<ReceiptItemResponse> items) {
        if (receipt == null) return null;

        return ReceiptResponse.builder()
                .id(receipt.getId())
                .merchantUid(receipt.getMerchantUid())
                .totalAmount(receipt.getTotalAmount())
                .couponDiscount(receipt.getCouponDiscount())
                .finalAmount(receipt.getFinalAmount())
                .memberName(receipt.getMemberName())
                .memberPhone(receipt.getMemberPhone())
                .status(receipt.getStatus())
                .paidAt(receipt.getPaidAt())
                .cancelledAt(receipt.getCancelledAt())
                .items(items)
                .paymentMethod("card")
                .build();
    }

    public static ReceiptItemResponse toItemResponse(OrderItem orderItem, String productName, String color, String size) {
        if (orderItem == null) return null;

        return ReceiptItemResponse.builder()
                .id(orderItem.getId())
                .storeItemDetailId(orderItem.getStoreItemDetailId())
                .productName(productName)
                .color(color)
                .size(size)
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }
}
