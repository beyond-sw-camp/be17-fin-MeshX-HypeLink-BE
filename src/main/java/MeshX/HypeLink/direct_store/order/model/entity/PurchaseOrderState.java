package MeshX.HypeLink.direct_store.order.model.entity;

import lombok.Getter;

@Getter
public enum PurchaseOrderState {
    REQUESTED("발주 요청됨"),
    COMPLETED("수령 완료"),
    CANCELED("요청 취소");

    private final String description;

    PurchaseOrderState(String description) {
        this.description = description;
    }
}
