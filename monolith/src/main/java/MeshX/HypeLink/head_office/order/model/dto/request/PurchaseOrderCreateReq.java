package MeshX.HypeLink.head_office.order.model.dto.request;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.head_office.item.model.entity.ItemDetail;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseDetailStatus;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrder;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrderState;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PurchaseOrderCreateReq {
    private String itemDetailCode;         // 상품 Code
    private String description;
    private Integer quantity;       // 수량
    private Integer requestStoreId;    // 요청 매장
    private Integer supplierStoreId;   // 공급 매장

    public PurchaseOrder toEntity(ItemDetail itemDetail, Member requester, Member supplier){
        return PurchaseOrder.builder()
                .itemDetail(itemDetail)
                .quantity(quantity)
                .purchaseDetailStatus(PurchaseDetailStatus.fromDescription(description))
                .purchaseOrderState(PurchaseOrderState.REQUESTED)
                .requester(requester)
                .supplier(supplier)
                .totalPrice(quantity * itemDetail.getItem().getUnitPrice())
                .build();
    }
}
