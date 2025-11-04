package MeshX.HypeLink.head_office.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 매장 상세 통계 DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDetailDTO {
    private Integer storeId;
    private String storeNumber;
    private String memberName;

    // 매출 정보
    private Long totalRevenue;
    private Long orderCount;
    private Long avgOrderValue;
    private Double growthRate;

    // 상품 정보
    private Long totalProducts;      // 취급 상품 수
    private Long topProductId;       // 베스트 상품 ID
    private String topProductName;   // 베스트 상품명

    // 주문 상태
    private Long pendingOrders;
    private Long completedOrders;
    private Double completionRate;
}
