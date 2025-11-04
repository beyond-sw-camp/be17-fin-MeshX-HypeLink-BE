package MeshX.HypeLink.head_office.analytics.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 매출 추이 DTO (일별/주별)
 */
@Data
@NoArgsConstructor
public class SalesTrendDTO {
    private LocalDate date;         // 날짜
    private Long totalRevenue;      // 해당일 매출
    private Long orderCount;        // 해당일 주문 수
    private Double avgOrderValue;     // 평균 주문 금액

    // QueryDSL용 생성자 (String을 LocalDate로 변환)
    public SalesTrendDTO(String dateStr, Long totalRevenue, Long orderCount, Double avgOrderValue) {
        this.date = LocalDate.parse(dateStr);
        this.totalRevenue = totalRevenue;
        this.orderCount = orderCount;
        this.avgOrderValue = avgOrderValue;
    }

    public SalesTrendDTO(LocalDate date, Long totalRevenue, Long orderCount, Double avgOrderValue) {
        this.date = date;
        this.totalRevenue = totalRevenue;
        this.orderCount = orderCount;
        this.avgOrderValue = avgOrderValue;
    }
}
