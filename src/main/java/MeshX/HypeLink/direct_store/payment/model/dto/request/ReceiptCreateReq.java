package MeshX.HypeLink.direct_store.payment.model.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ReceiptCreateReq {
    private Integer storeId;
    private Integer memberId;
    private String memberName;
    private String memberPhone;
    private Integer couponDiscount;
    private Integer customerCouponId;  // 사용한 고객 쿠폰 ID
    private List<ReceiptItemDto> items;
}
