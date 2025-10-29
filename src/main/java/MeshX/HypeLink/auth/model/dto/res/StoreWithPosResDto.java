package MeshX.HypeLink.auth.model.dto.res;

import MeshX.HypeLink.auth.model.PosInfoDto;
import MeshX.HypeLink.auth.model.entity.Region;
import MeshX.HypeLink.auth.model.entity.StoreState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class StoreWithPosResDto {
    private Integer id;
    private String name;
    private String address;
    private Region region;
    private StoreState storeState;
    private List<PosInfoDto> posDevices;

}
