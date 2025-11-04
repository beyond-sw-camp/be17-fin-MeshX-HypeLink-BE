package MeshX.HypeLink.head_office.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 고객 RFM 분석 DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRFMDTO {
    private Long customerId;
    private String customerName;
    
    private String customerPhone;

    // RFM 점수
    private Integer recencyScore;      // 최근성 점수 (1-5)
    private Integer frequencyScore;    // 빈도 점수 (1-5)
    private Integer monetaryScore;     // 금액 점수 (1-5)
    private Double rfmScore;           // 종합 RFM 점수

    // 실제 값
    private Integer daysSinceLastOrder;  // 마지막 주문 이후 일수
    private Long totalOrders;            // 총 주문 횟수
    private Long totalSpent;             // 총 구매 금액

    // 세그먼트
    private String segment;  // VIP, 충성고객, 잠재고객, 이탈위험, 휴면고객
    private String segmentDescription;
}
