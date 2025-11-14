package com.example.apidirect.payment.adapter.out.mapper;

import com.example.apidirect.payment.adapter.out.entity.PaymentsEntity;
import com.example.apidirect.payment.domain.Payments;

public class PaymentsMapper {

    public static Payments toDomain(PaymentsEntity entity) {
        if (entity == null) return null;

        return Payments.builder()
                .id(entity.getId())
                .customerReceiptId(entity.getCustomerReceiptId())
                .paymentId(entity.getPaymentId())
                .transactionId(entity.getTransactionId())
                .storeId(entity.getStoreId())
                .channelKey(entity.getChannelKey())
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .paidAt(entity.getPaidAt())
                .failureReason(entity.getFailureReason())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static PaymentsEntity toEntity(Payments domain) {
        if (domain == null) return null;

        return PaymentsEntity.builder()
                .id(domain.getId())
                .customerReceiptId(domain.getCustomerReceiptId())
                .paymentId(domain.getPaymentId())
                .transactionId(domain.getTransactionId())
                .storeId(domain.getStoreId())
                .channelKey(domain.getChannelKey())
                .amount(domain.getAmount())
                .status(domain.getStatus())
                .paidAt(domain.getPaidAt())
                .failureReason(domain.getFailureReason())
                .build();
    }
}
