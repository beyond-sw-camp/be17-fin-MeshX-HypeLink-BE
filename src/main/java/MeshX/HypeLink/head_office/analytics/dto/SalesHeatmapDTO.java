package MeshX.HypeLink.head_office.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 시간대별 매출 히트맵 DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesHeatmapDTO {
    private Integer dayOfWeek;      // 요일 (1=월요일, 7=일요일)
    private Integer hourOfDay;      // 시간 (0-23)
    private Long totalRevenue;      // 해당 시간대 매출
    private Long orderCount;        // 해당 시간대 주문 수
}
