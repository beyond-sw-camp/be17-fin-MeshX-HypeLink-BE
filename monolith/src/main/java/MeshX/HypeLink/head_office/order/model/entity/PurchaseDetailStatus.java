package MeshX.HypeLink.head_office.order.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PurchaseDetailStatus {
    STOCK_SHORTAGE("STOCK_SHORTAGE", "재고 부족으로 인한 발주"),
    NEW_PRODUCT("NEW_PRODUCT", "신규 상품 입고 요청"),
    SEASON_CHANGE("SEASON_CHANGE", "시즌 변경에 따른 교체 발주"),
    STORE_OPEN("STORE_OPEN", "신규 매장 오픈용 발주"),
    STORE_EVENT("STORE_EVENT", "매장 이벤트(행사, 프로모션)용 발주"),
    DAMAGED_REPLACEMENT("DAMAGED_REPLACEMENT", "파손/불량 교체용 발주"),
    RETURNED_REPLENISH("RETURNED_REPLENISH", "반품 또는 회수 후 재보충 발주"),
    URGENT_REQUEST("URGENT_REQUEST", "긴급 요청(재고 소진 등)"),
    HQ_PLANNED_ORDER("HQ_PLANNED_ORDER", "본사 계획에 따른 자동 발주"),
    DAMAGED_CANCELLATION("DAMAGED_CANCELLATION", "파손/불량으로 인한 발주 취소"),
    OTHER_CANCELLATION("OTHER_CANCELLATION", "기타 사유로 인한 발주 취소"),
    STORE_TRANSFER("STORE_TRANSFER", "매장 간 발주 이동");

    private final String code;        // 시스템에서 사용할 코드
    private final String description; // 화면/로그용 한글 설명

    // code 값으로 Enum 찾기
    public static PurchaseDetailStatus fromCode(String code) {
        for (PurchaseDetailStatus status : values()) {
            if (status.getCode().equalsIgnoreCase(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 발주 코드입니다: " + code);
    }

    // description(한글 설명)으로 Enum 찾기
    public static PurchaseDetailStatus fromDescription(String description) {
        for (PurchaseDetailStatus status : values()) {
            if (status.getDescription().equals(description)) {
                return status;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 발주 사유입니다: " + description);
    }

    // code나 description 중 아무거나 입력해도 인식되는 통합 메서드
    public static PurchaseDetailStatus resolve(String input) {
        for (PurchaseDetailStatus status : values()) {
            if (status.getCode().equalsIgnoreCase(input) || status.getDescription().equals(input)) {
                return status;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 발주 상태 입력입니다: " + input);
    }
}