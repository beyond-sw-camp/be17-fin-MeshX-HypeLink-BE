package MeshX.HypeLink.direct_store.payment.model.dto.response;

import MeshX.HypeLink.head_office.customer.model.entity.CustomerReceipt;
import MeshX.HypeLink.head_office.customer.model.entity.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReceiptDetailRes {
    private Integer id;
    private String merchantUid;
    private Integer storeId;
    private Integer memberId;
    private String memberName;
    private String memberPhone;
    private Integer totalAmount;
    private Integer discountAmount;
    private Integer couponDiscount;
    private Integer finalAmount;
    private PaymentStatus status;
    private List<ReceiptItemRes> items;
    private LocalDateTime createdAt;

    public static ReceiptDetailRes toDto(CustomerReceipt receipt) {
        List<ReceiptItemRes> itemResList = receipt.getOrderItems().stream()
                .map(ReceiptItemRes::toDto)
                .collect(Collectors.toList());

        return ReceiptDetailRes.builder()
                .id(receipt.getId())
                .merchantUid(receipt.getMerchantUid())
                .storeId(receipt.getStore() != null ? receipt.getStore().getId() : null)
                .memberId(receipt.getCustomer() != null ? receipt.getCustomer().getId() : null)
                .memberName(receipt.getMemberName())
                .memberPhone(receipt.getMemberPhone())
                .totalAmount(receipt.getTotalAmount())
                .discountAmount(receipt.getDiscountAmount())
                .couponDiscount(receipt.getCouponDiscount())
                .finalAmount(receipt.getFinalAmount())
                .status(receipt.getStatus())
                .items(itemResList)
                .createdAt(receipt.getCreatedAt())
                .build();
    }

    @Builder
    private ReceiptDetailRes(Integer id, String merchantUid, Integer storeId, Integer memberId,
                             String memberName, String memberPhone, Integer totalAmount,
                             Integer discountAmount, Integer couponDiscount,
                             Integer finalAmount, PaymentStatus status, List<ReceiptItemRes> items,
                             LocalDateTime createdAt) {
        this.id = id;
        this.merchantUid = merchantUid;
        this.storeId = storeId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberPhone = memberPhone;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.couponDiscount = couponDiscount;
        this.finalAmount = finalAmount;
        this.status = status;
        this.items = items;
        this.createdAt = createdAt;
    }
}
