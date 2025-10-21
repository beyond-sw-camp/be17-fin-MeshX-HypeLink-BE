package MeshX.HypeLink.head_office.order.model.dto.request;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.head_office.item.model.entity.Item;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrder;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrderState;
import lombok.Getter;

@Getter
public class HeadPurchaseOrderCreateReq {
    private Integer itemId;         // 상품 Id
    private Integer quantity;       // 수량

    public PurchaseOrder toEntity(Item item, Member requester, Member supplier){
        return PurchaseOrder.builder()
                .item(item)
                .quantity(quantity)
                .purchaseOrderState(PurchaseOrderState.REQUESTED)
                .requester(requester)
                .supplier(supplier)
                .totalPrice(quantity * item.getUnitPrice())
                .build();
    }
}
