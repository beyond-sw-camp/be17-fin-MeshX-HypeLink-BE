package MeshX.HypeLink.head_office.promotion.model.entity;

import MeshX.HypeLink.head_office.order.model.entity.PurchaseDetailStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PromotionStatus {
    UPCOMING("예정"),   // 예정
    ONGOING("진행중"),    // 진행중
    ENDED("종료");      // 종료

    private final String description;

    // description(한글 설명)으로 Enum 찾기
    public static PromotionStatus fromDescription(String description) {
        for (PromotionStatus status : values()) {
            if (status.getDescription().equals(description)) {
                return status;
            }
        }
        return null;
    }
}
