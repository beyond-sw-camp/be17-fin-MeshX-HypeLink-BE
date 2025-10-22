package MeshX.HypeLink.direct_store.posOrder.model.dto.request;

import MeshX.HypeLink.direct_store.posOrder.model.entity.PosOrder;
import MeshX.HypeLink.direct_store.posOrder.model.entity.PosOrderStatus;
import lombok.Getter;

import java.util.List;

@Getter
public class PosOrderCreateReq {
    private Integer storeId;
    private Integer memberId;
    private String memberName;
    private String memberPhone;
    private Integer pointsUsed;        // TODO: DB 검증 필요 - 사용자의 실제 보유 포인트 확인
    private Integer couponDiscount;    // TODO: 삭제 필요 - couponId로 받아서 DB에서 할인 금액 조회해야 함
    // TODO: 실제 배포 시 추가 필요: private Integer couponId;
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
