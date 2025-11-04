package MeshX.HypeLink.head_office.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 주문 현황 DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOverviewDTO {
    private Long totalOrders;       // 총 주문 수
    private Long pendingOrders;     // 대기 중 주문
    private Long completedOrders;   // 완료된 주문
    private Long cancelledOrders;   // 취소된 주문
    private Double completionRate;  // 완료율 (%)
}
