package MeshX.HypeLink.head_office.customer.model.dto.response;

import MeshX.HypeLink.head_office.customer.model.entity.CustomerReceipt;
import MeshX.HypeLink.head_office.customer.model.entity.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ReceiptRes {
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
    private List<ReceiptItemRes> items;
    private String paymentMethod;

    public static ReceiptRes toDto(CustomerReceipt receipt) {
        List<ReceiptItemRes> items = receipt.getOrderItems().stream()
                .map(ReceiptItemRes::toDto)
                .collect(Collectors.toList());

        return ReceiptRes.builder()
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
                .paymentMethod("card") // TODO: Payments 테이블에서 실제 결제 수단 가져오기
                .build();
    }
}
