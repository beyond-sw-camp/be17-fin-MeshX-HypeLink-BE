package MeshX.HypeLink.head_office.order.model.dto.request;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.head_office.item.model.entity.ItemDetail;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseDetailStatus;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrder;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrderState;
import lombok.Getter;

@Getter
public class HeadPurchaseOrderCreateReq {
    private Integer itemDetailId;         // 상품 Id
    private String description;
    private Integer quantity;       // 수량

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
