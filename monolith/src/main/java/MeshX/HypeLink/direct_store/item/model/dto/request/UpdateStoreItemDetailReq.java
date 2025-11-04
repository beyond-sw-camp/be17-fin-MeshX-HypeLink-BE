package MeshX.HypeLink.direct_store.item.model.dto.request;

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
