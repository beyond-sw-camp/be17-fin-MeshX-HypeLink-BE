package MeshX.HypeLink.head_office.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 매출 차트 데이터 DTO (Sales Management 페이지 차트용)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesChartDataDTO {
    private String storeName;              // "전체" 또는 매장명
    private List<String> categories;       // ['월', '화', '수', '목', '금', '토', '일']
    private List<Long> values;             // 각 요일별 매출액
}
