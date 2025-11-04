package MeshX.HypeLink.auth.model.dto.req;

import MeshX.HypeLink.auth.model.PosInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
public class StoreWithPosListReqDto {
    private Integer id;
    private String name;
    private List<PosInfoDto> posDevices;

}
