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
    private String urgency;                    // critical, high, medium, low
    private Integer recommendedOrderQuantity;  // 권장 발주량
    private Integer daysUntilStockout;        // 소진 예상일 (일 단위)

    // 기존 생성자 호환용 (7개 파라미터)
    public LowStockItemDTO(Integer itemDetailId, String itemCode, String itemName,
                          String categoryName, String colorName, String sizeName,
                          Integer currentStock, String urgency) {
        this.itemDetailId = itemDetailId;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.categoryName = categoryName;
        this.colorName = colorName;
        this.sizeName = sizeName;
        this.currentStock = currentStock;
        this.urgency = urgency;
        this.recommendedOrderQuantity = 0;
        this.daysUntilStockout = 0;
    }
}
