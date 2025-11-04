package MeshX.HypeLink.head_office.order.model.dto.response;

import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrder;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PurchaseOrderInfoDetailListRes {
    private List<PurchaseOrderInfoDetailRes> headOrders;

    public static PurchaseOrderInfoDetailListRes toDto(List<PurchaseOrder> entity) {
        return PurchaseOrderInfoDetailListRes.builder()
                .headOrders(entity.stream()
                        .map(PurchaseOrderInfoDetailRes::toDto)
                        .toList()
                )
                .build();
    }

    @Builder
    private PurchaseOrderInfoDetailListRes(List<PurchaseOrderInfoDetailRes> headOrders) {
        this.headOrders = headOrders;
    }
}
