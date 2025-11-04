package MeshX.HypeLink.head_office.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 연령대별 고객 분포 DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgeDistributionDTO {
    private String ageGroup;      // "10대", "20대", "30대", "40대", "50대 이상"
    private Long customerCount;   // 해당 연령대 고객 수
}
