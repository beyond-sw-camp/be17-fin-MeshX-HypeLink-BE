package com.example.apidirect.auth.adapter.out.external.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReceiptSyncDto {
    private Integer id;
    private String pgProvider;
    private String pgTid;
    private String merchantUid;
    private Integer totalAmount;
    private Integer discountAmount;
    private Integer couponDiscount;
    private Integer finalAmount;
    private Integer storeId;
    private Integer customerId;
    private String memberName;
    private String memberPhone;
    private String status;
    private LocalDateTime paidAt;
    private LocalDateTime cancelledAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
