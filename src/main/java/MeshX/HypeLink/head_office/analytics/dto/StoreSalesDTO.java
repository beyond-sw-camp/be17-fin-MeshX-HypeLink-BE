package MeshX.HypeLink.head_office.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 매장별 매출 DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreSalesDTO {
    private Integer storeId;
    private String storeNumber;
    private String memberName;      // 매장주 이름
    private Long totalRevenue;
    private Long orderCount;
    private Double growthRate;
}
