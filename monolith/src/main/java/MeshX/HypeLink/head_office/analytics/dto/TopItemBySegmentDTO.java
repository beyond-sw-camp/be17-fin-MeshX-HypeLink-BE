package MeshX.HypeLink.head_office.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 세그먼트별 (연령대/카테고리) 인기 품목 DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopItemBySegmentDTO {
    private String itemName;
    private String category;
    private Long salesCount;      // 판매 횟수
    private Long salesAmount;     // 매출액
}
