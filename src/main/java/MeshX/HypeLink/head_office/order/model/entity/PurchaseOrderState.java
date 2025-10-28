package MeshX.HypeLink.head_office.order.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PurchaseOrderState {
    REQUESTED("발주 요청됨"),
    COMPLETED("수령 완료"),
    CANCELED("요청 취소");

    private final String description;

    // description(한글 설명)으로 Enum 찾기
    public static PurchaseOrderState fromDescription(String description) {
        for (PurchaseOrderState status : values()) {
            if (status.getDescription().equals(description)) {
                return status;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 상태입니다: " + description);
    }
}
