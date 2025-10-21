package MeshX.HypeLink.auth.model.dto.res;

import MeshX.HypeLink.auth.model.entity.StoreState;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreListResDto {
    private Integer storeId;
    private String storeName;
    private String storeAddress;
    private String storePhone;
    private StoreState storeState;
}
