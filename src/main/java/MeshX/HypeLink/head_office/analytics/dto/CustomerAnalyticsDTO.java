package MeshX.HypeLink.head_office.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 고객 분석 데이터 DTO (Customer Analytics 페이지용)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAnalyticsDTO {
    private Long customerId;
    private String name;
    private String phone;
    private String birthday;             // yyyy-MM-dd 형식
    private Long totalPurchases;         // 총 구매액
    private String lastPurchase;         // 최근 구매일 (yyyy-MM-dd)
    private List<PurchaseHistoryItem> purchaseHistory;  // 구매 이력

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PurchaseHistoryItem {
        private String category;
        private String itemName;
        private Integer quantity;
        private Long amount;
    }
}
