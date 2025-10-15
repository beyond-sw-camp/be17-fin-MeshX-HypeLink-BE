package MeshX.HypeLink.direct_store.posOrder.model.dto.request;

import MeshX.HypeLink.direct_store.pos.posOrder.model.entity.PosOrder;
import MeshX.HypeLink.direct_store.pos.posOrder.model.entity.PosOrderStatus;
import lombok.Getter;

import java.util.List;

@Getter
public class PosOrderCreateReq {
    private Integer storeId;
    private Integer memberId;
    private String memberName;
    private String memberPhone;
    private Integer pointsUsed;
    private Integer couponDiscount;
    private List<PosOrderItemDto> items;

    public PosOrder toEntity(String orderNumber, int totalAmount, int discountAmount, int finalAmount) {
        return PosOrder.builder()
                .orderNumber(orderNumber)
                .storeId(storeId)
                .memberId(memberId)
                .memberName(memberName)
                .memberPhone(memberPhone)
                .totalAmount(totalAmount)
                .discountAmount(discountAmount)
                .pointsUsed(pointsUsed)
                .couponDiscount(couponDiscount)
                .finalAmount(finalAmount)
                .status(PosOrderStatus.PENDING)
                .build();
    }
}
