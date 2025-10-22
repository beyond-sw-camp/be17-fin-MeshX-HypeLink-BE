package MeshX.HypeLink.auth.model.dto.res;

import MeshX.HypeLink.auth.model.entity.Region;
import MeshX.HypeLink.auth.model.entity.Store;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreInfoResDto {
    private String name;
    private String phone;
    private String address; // 매장주소
    private Region region;
    private String storeNumber;

}
