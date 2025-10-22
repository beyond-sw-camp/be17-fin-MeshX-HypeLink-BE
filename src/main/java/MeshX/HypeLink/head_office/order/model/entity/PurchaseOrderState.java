package MeshX.HypeLink.head_office.order.model.entity;

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
