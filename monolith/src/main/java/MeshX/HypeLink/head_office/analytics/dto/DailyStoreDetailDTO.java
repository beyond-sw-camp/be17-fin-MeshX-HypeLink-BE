package MeshX.HypeLink.head_office.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyStoreDetailDTO {
    private Integer storeId;
    private String storeName;
    private String storeNumber;
    private Long amount;
}
