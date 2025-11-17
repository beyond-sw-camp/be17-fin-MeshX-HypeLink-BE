package com.example.apidirect.payment.adapter.in.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import com.example.apidirect.payment.domain.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReceiptResponse {
    private Integer id;
    private String merchantUid;
    private Integer totalAmount;
    private Integer couponDiscount;
    private Integer finalAmount;
    private String memberName;
    private String memberPhone;
    private PaymentStatus status;
    private LocalDateTime paidAt;
    private LocalDateTime cancelledAt;
    private List<ReceiptItemResponse> items;
    private String paymentMethod;
}
