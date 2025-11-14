package com.example.apidirect.payment.adapter.out.kafka.mapper;

import com.example.apidirect.payment.adapter.out.kafka.dto.CustomerReceiptData;
import com.example.apidirect.payment.adapter.out.kafka.dto.OrderItemData;
import com.example.apidirect.payment.adapter.out.kafka.dto.PaymentData;
import com.example.apidirect.payment.adapter.out.kafka.dto.PaymentSyncEvent;
import com.example.apidirect.payment.domain.CustomerReceipt;
import com.example.apidirect.payment.domain.OrderItem;
import com.example.apidirect.payment.domain.Payments;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentSyncMapper {

    public static PaymentSyncEvent toEvent(CustomerReceipt receipt, List<OrderItem> orderItems, Payments payment) {
        return PaymentSyncEvent.builder()
                .storeId(receipt.getStoreId())
                .receipt(toReceiptData(receipt))
                .orderItems(toOrderItemDataList(orderItems))
                .payment(toPaymentData(payment))
                .build();
    }

    public static CustomerReceiptData toReceiptData(CustomerReceipt receipt) {
        return CustomerReceiptData.builder()
                .id(receipt.getId())
                .pgProvider(receipt.getPgProvider())
                .pgTid(receipt.getPgTid())
                .merchantUid(receipt.getMerchantUid())
                .totalAmount(receipt.getTotalAmount())
                .discountAmount(receipt.getDiscountAmount())
                .couponDiscount(receipt.getCouponDiscount())
                .finalAmount(receipt.getFinalAmount())
                .storeId(receipt.getStoreId())
                .customerId(receipt.getCustomerId())
                .memberName(receipt.getMemberName())
                .memberPhone(receipt.getMemberPhone())
                .posCode(receipt.getPosCode())
                .status(receipt.getStatus() != null ? receipt.getStatus().name() : null)
                .paidAt(receipt.getPaidAt())
                .cancelledAt(receipt.getCancelledAt())
                .build();
    }

    public static List<OrderItemData> toOrderItemDataList(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(PaymentSyncMapper::toOrderItemData)
                .collect(Collectors.toList());
    }

    public static OrderItemData toOrderItemData(OrderItem orderItem) {
        return OrderItemData.builder()
                .id(orderItem.getId())
                .customerReceiptId(orderItem.getCustomerReceiptId())
                .storeItemDetailId(orderItem.getStoreItemDetailId())
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }

    public static PaymentData toPaymentData(Payments payment) {
        return PaymentData.builder()
                .id(payment.getId())
                .customerReceiptId(payment.getCustomerReceiptId())
                .paymentId(payment.getPaymentId())
                .transactionId(payment.getTransactionId())
                .storeId(payment.getStoreId())
                .channelKey(payment.getChannelKey())
                .amount(payment.getAmount())
                .status(payment.getStatus() != null ? payment.getStatus().name() : null)
                .paidAt(payment.getPaidAt())
                .failureReason(payment.getFailureReason())
                .build();
    }
}
