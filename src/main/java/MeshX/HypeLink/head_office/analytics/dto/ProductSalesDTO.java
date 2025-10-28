package MeshX.HypeLink.head_office.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 상품별 매출 DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSalesDTO {
    private Integer itemId;
    private String itemCode;
    private String koName;
    private String enName;
    private String categoryName;
    private Long totalRevenue;
    private Long quantity;
    private Double avgPrice;
    private Double growthRate;
}
