package MeshX.HypeLink.head_office.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 카테고리별 고객 매출 DTO (Customer Analytics 페이지용)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCustomerSalesDTO {
    private String category;      // "상의", "하의", "아우터", "악세서리" 등
    private Long salesAmount;     // 카테고리별 매출액
}
