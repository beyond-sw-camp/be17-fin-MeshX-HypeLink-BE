package MeshX.HypeLink.head_office.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 상품 상세 통계 DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDTO {
    private Integer itemId;
    private String itemCode;
    private String koName;
    private String enName;
    private String categoryName;

    // 판매 정보
    private Long totalRevenue;
    private Long totalQuantity;
    private Long avgPrice;
    private Double growthRate;

    // 재고 정보
    private Long totalStock;         // 전체 재고
    private Long lowStockCount;      // 재고 부족 건수

    // 인기도
    private Long orderCount;         // 주문 건수
    private Long topStoreId;         // 베스트 매장 ID
    private String topStoreName;     // 베스트 매장명
}
