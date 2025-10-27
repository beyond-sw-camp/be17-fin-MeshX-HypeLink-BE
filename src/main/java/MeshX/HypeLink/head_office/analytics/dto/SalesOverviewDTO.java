package MeshX.HypeLink.head_office.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 매출 현황 DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesOverviewDTO {
    private Long totalRevenue;      // 총 매출
    private Long totalTransactions; // 총 거래 수
    private Long avgOrderValue;     // 평균 주문 금액
    private Double growthRate;      // 성장률 (%)

    // 비교 데이터
    private Long lastMonthRevenue;   // 전월 매출
    private Long lastYearRevenue;    // 전년 매출
    private Double monthOverMonthGrowth;  // 전월 대비 성장률 (%)
    private Double yearOverYearGrowth;    // 전년 대비 성장률 (%)

    // 기본 4개 필드 생성자
    public SalesOverviewDTO(Long totalRevenue, Long totalTransactions, Long avgOrderValue, Double growthRate) {
        this.totalRevenue = totalRevenue;
        this.totalTransactions = totalTransactions;
        this.avgOrderValue = avgOrderValue;
        this.growthRate = growthRate;
        this.lastMonthRevenue = 0L;
        this.lastYearRevenue = 0L;
        this.monthOverMonthGrowth = 0.0;
        this.yearOverYearGrowth = 0.0;
    }
}
