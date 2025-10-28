package MeshX.HypeLink.head_office.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 카테고리별 성과 DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPerformanceDTO {
    private String categoryName;
    private Long revenue;
    private Long quantity;
    private Long avgPrice;
    private Double growthRate;
}
