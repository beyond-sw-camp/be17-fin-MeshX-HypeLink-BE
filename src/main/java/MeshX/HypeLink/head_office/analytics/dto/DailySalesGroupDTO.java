package MeshX.HypeLink.head_office.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 날짜별 매출 그룹 DTO (날짜별로 그룹화된 매출 정보)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailySalesGroupDTO {
    private String date;                              // yyyy-MM-dd 형식
    private Long totalAmount;                          // 해당 날짜의 전체 매출 합계
    private Integer storeCount;                        // 해당 날짜에 매출이 있는 가맹점 수
    private List<DailyStoreDetailDTO> stores;          // 가맹점별 상세 정보
}
