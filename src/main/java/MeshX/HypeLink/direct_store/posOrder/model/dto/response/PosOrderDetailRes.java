package MeshX.HypeLink.direct_store.posOrder.model.dto.response;

import MeshX.HypeLink.direct_store.pos.posOrder.model.entity.PosOrder;
import MeshX.HypeLink.direct_store.pos.posOrder.model.entity.PosOrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PosOrderDetailRes {
    private Integer id;
    private String orderNumber;
    private Integer storeId;
    private Integer memberId;
    private String memberName;
    private String memberPhone;
    private Integer totalAmount;
    private Integer discountAmount;
    private Integer pointsUsed;
    private Integer couponDiscount;
    private Integer finalAmount;
    private PosOrderStatus status;
    private List<PosOrderItemRes> items;
    private LocalDateTime createdAt;

    public static PosOrderDetailRes toDto(PosOrder entity) {
        List<PosOrderItemRes> itemResList = entity.getPosOrderItems().stream()
                .map(PosOrderItemRes::toDto)
                .collect(Collectors.toList());

        return PosOrderDetailRes.builder()
                .id(entity.getId())
                .orderNumber(entity.getOrderNumber())
                .storeId(entity.getStoreId())
                .memberId(entity.getMemberId())
                .memberName(entity.getMemberName())
                .memberPhone(entity.getMemberPhone())
                .totalAmount(entity.getTotalAmount())
                .discountAmount(entity.getDiscountAmount())
                .pointsUsed(entity.getPointsUsed())
                .couponDiscount(entity.getCouponDiscount())
                .finalAmount(entity.getFinalAmount())
                .status(entity.getStatus())
                .items(itemResList)
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @Builder
    private PosOrderDetailRes(Integer id, String orderNumber, Integer storeId, Integer memberId,
                           String memberName, String memberPhone, Integer totalAmount,
                           Integer discountAmount, Integer pointsUsed, Integer couponDiscount,
                           Integer finalAmount, PosOrderStatus status, List<PosOrderItemRes> items,
                           LocalDateTime createdAt) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.storeId = storeId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberPhone = memberPhone;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.pointsUsed = pointsUsed;
        this.couponDiscount = couponDiscount;
        this.finalAmount = finalAmount;
        this.status = status;
        this.items = items;
        this.createdAt = createdAt;
    }
}