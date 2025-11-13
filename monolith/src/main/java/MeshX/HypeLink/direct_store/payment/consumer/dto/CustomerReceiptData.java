package MeshX.HypeLink.direct_store.payment.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReceiptData {
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
    private String posCode;
    private String status;
    private LocalDateTime paidAt;
    private LocalDateTime cancelledAt;
}
