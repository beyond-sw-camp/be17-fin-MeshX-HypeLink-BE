package MeshX.HypeLink.head_office.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 상품 ABC 분석 DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductABCDTO {
    private Long productId;
    private String productName;
    private String categoryName;

    // 매출 데이터
    private Long totalRevenue;           // 총 매출
    private Long totalQuantity;          // 총 판매량
    private Double revenuePercentage;    // 전체 매출 대비 비율
    private Double cumulativePercentage; // 누적 매출 비율

    // ABC 등급
    private String abcGrade;  // A(상위 80%), B(다음 15%), C(나머지 5%)
    private Integer ranking;  // 순위

    // 분석 정보
    private String recommendation;  // 관리 권장사항
}
