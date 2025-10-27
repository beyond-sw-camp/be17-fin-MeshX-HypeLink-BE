package MeshX.HypeLink.head_office.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 재고 부족 품목 DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LowStockItemDTO {
    private Integer itemDetailId;
    private String itemCode;
    private String itemName;
    private String categoryName;
    private String colorName;
    private String sizeName;
    private Integer currentStock;
    private String urgency;         // critical, high, medium
}
