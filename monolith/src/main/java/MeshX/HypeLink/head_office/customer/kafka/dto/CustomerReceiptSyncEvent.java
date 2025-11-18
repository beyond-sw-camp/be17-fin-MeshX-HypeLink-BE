package MeshX.HypeLink.head_office.customer.kafka.dto;

import MeshX.HypeLink.head_office.customer.model.entity.CustomerReceipt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReceiptSyncEvent {
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

    public static CustomerReceiptSyncEvent from(CustomerReceipt receipt) {
        return CustomerReceiptSyncEvent.builder()
                .id(receipt.getId())
                .pgProvider(receipt.getPgProvider())
                .pgTid(receipt.getPgTid())
                .merchantUid(receipt.getMerchantUid())
                .totalAmount(receipt.getTotalAmount())
                .discountAmount(receipt.getDiscountAmount())
                .couponDiscount(receipt.getCouponDiscount())
                .finalAmount(receipt.getFinalAmount())
                .storeId(receipt.getStore() != null ? receipt.getStore().getId() : null)
                .customerId(receipt.getCustomer() != null ? receipt.getCustomer().getId() : null)
                .memberName(receipt.getMemberName())
                .memberPhone(receipt.getMemberPhone())
                .status(receipt.getStatus() != null ? receipt.getStatus().name() : null)
                .paidAt(receipt.getPaidAt())
                .cancelledAt(receipt.getCancelledAt())
                .createdAt(receipt.getCreatedAt())
                .updatedAt(receipt.getUpdatedAt())
                .build();
    }
}
