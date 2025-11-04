package MeshX.HypeLink.head_office.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 일별 매출 데이터 DTO (Sales Management 페이지용)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailySalesDTO {
    private Long id;
    private Integer storeId;
    private String storeName;
    private String storeNumber;
    private String date;          // yyyy-MM-dd 형식
    private Long amount;          // 매출액
}
