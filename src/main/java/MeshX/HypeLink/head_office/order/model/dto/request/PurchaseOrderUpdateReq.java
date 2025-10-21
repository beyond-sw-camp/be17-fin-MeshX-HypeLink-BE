package MeshX.HypeLink.head_office.order.model.dto.request;

import lombok.Getter;

@Getter
public class PurchaseOrderUpdateReq {
    private Integer orderId;
    private String orderState;
}
