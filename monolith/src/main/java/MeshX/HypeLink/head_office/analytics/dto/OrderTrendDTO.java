package MeshX.HypeLink.head_office.analytics.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 주문 추이 DTO (일별/주별)
 */
@Data
@NoArgsConstructor
public class OrderTrendDTO {
    private LocalDate date;
    private Long totalOrders;
    private Long pendingOrders;
    private Long completedOrders;
    private Long cancelledOrders;

    // QueryDSL용 생성자 (String을 LocalDate로 변환)
    public OrderTrendDTO(String dateStr, Long totalOrders, Long pendingOrders, Long completedOrders, Long cancelledOrders) {
        this.date = LocalDate.parse(dateStr);
        this.totalOrders = totalOrders;
        this.pendingOrders = pendingOrders;
        this.completedOrders = completedOrders;
        this.cancelledOrders = cancelledOrders;
    }

    public OrderTrendDTO(LocalDate date, Long totalOrders, Long pendingOrders, Long completedOrders, Long cancelledOrders) {
        this.date = date;
        this.totalOrders = totalOrders;
        this.pendingOrders = pendingOrders;
        this.completedOrders = completedOrders;
        this.cancelledOrders = cancelledOrders;
    }
}
