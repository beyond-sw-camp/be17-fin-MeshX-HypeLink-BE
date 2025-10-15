package MeshX.HypeLink.direct_store.posOrder.model.dto.response;

import MeshX.HypeLink.direct_store.posOrder.model.entity.PosOrder;
import MeshX.HypeLink.direct_store.posOrder.model.entity.PosOrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PosOrderInfoRes {
    private Integer id;
    private String orderNumber;
    private Integer storeId;
    private String memberName;
    private String memberPhone;
    private Integer finalAmount;
    private PosOrderStatus status;
    private LocalDateTime createdAt;

    public static PosOrderInfoRes toDto(PosOrder entity) {
        return PosOrderInfoRes.builder()
                .id(entity.getId())
                .orderNumber(entity.getOrderNumber())
                .storeId(entity.getStoreId())
                .memberName(entity.getMemberName())
                .memberPhone(entity.getMemberPhone())
                .finalAmount(entity.getFinalAmount())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @Builder
    private PosOrderInfoRes(Integer id, String orderNumber, Integer storeId,
                         String memberName, String memberPhone, Integer finalAmount,
                         PosOrderStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.storeId = storeId;
        this.memberName = memberName;
        this.memberPhone = memberPhone;
        this.finalAmount = finalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }
}