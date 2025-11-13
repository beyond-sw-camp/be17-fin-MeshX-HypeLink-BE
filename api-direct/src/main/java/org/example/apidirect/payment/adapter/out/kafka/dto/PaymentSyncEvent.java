package org.example.apidirect.payment.adapter.out.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSyncEvent {
    private Integer storeId;
    private CustomerReceiptData receipt;
    private List<OrderItemData> orderItems;
    private PaymentData payment;
}
