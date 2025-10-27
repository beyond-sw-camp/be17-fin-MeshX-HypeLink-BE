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
    private List<ReceiptItemDto> items;
}
