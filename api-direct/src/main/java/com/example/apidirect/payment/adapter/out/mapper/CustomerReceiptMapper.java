package com.example.apidirect.payment.adapter.out.mapper;

import com.example.apidirect.payment.adapter.out.entity.CustomerReceiptEntity;
import com.example.apidirect.payment.domain.CustomerReceipt;

import java.util.stream.Collectors;

public class CustomerReceiptMapper {

    public static CustomerReceipt toDomain(CustomerReceiptEntity entity) {
        if (entity == null) return null;

        return CustomerReceipt.builder()
                .id(entity.getId())
                .pgProvider(entity.getPgProvider())
                .pgTid(entity.getPgTid())
                .merchantUid(entity.getMerchantUid())
                .totalAmount(entity.getTotalAmount())
                .discountAmount(entity.getDiscountAmount())
                .couponDiscount(entity.getCouponDiscount())
                .finalAmount(entity.getFinalAmount())
                .storeId(entity.getStoreId())
                .customerId(entity.getCustomerId())
                .memberName(entity.getMemberName())
                .memberPhone(entity.getMemberPhone())
                .posCode(entity.getPosCode())
                .status(entity.getStatus())
                .paidAt(entity.getPaidAt())
                .cancelledAt(entity.getCancelledAt())
                .orderItems(entity.getOrderItems().stream()
                        .map(OrderItemMapper::toDomain)
                        .collect(Collectors.toList()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static CustomerReceiptEntity toEntity(CustomerReceipt domain) {
        if (domain == null) return null;

        return CustomerReceiptEntity.builder()
                .id(domain.getId())
                .pgProvider(domain.getPgProvider())
                .pgTid(domain.getPgTid())
                .merchantUid(domain.getMerchantUid())
                .totalAmount(domain.getTotalAmount())
                .discountAmount(domain.getDiscountAmount())
                .couponDiscount(domain.getCouponDiscount())
                .finalAmount(domain.getFinalAmount())
                .storeId(domain.getStoreId())
                .customerId(domain.getCustomerId())
                .memberName(domain.getMemberName())
                .memberPhone(domain.getMemberPhone())
                .posCode(domain.getPosCode())
                .status(domain.getStatus())
                .paidAt(domain.getPaidAt())
                .build();
    }
}
