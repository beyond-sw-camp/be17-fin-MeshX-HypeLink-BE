package MeshX.HypeLink.head_office.order.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateStoreItemDetailReq {
    private Integer storeId;
    private String itemCode;
    private String itemDetailCode;
    private Integer updateStock;
}
