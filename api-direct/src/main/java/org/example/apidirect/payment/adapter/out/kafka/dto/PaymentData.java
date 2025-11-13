package org.example.apidirect.payment.adapter.out.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentData {
    private Integer id;
    private Integer customerReceiptId;
    private String paymentId;
    private String transactionId;
    private String storeId;
    private String channelKey;
    private Integer amount;
    private String status;
    private LocalDateTime paidAt;
    private String failureReason;
}
