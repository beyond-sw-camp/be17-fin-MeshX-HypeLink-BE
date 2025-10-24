package MeshX.HypeLink.direct_store.payment.model.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ReceiptCreateReq {
    private Integer storeId;
    private Integer memberId;
    private String memberName;
    private String memberPhone;
    private Integer couponDiscount;    // TODO: 삭제 필요 - couponId로 받아서 DB에서 할인 금액 조회해야 함
    // TODO: 실제 배포 시 추가 필요: private Integer couponId;
    private List<ReceiptItemDto> items;
}
