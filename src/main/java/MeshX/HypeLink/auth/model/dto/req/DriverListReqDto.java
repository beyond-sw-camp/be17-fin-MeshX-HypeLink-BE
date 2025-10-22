package MeshX.HypeLink.auth.model.dto.req;

import MeshX.HypeLink.auth.model.entity.Region;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DriverListReqDto {

    private String name;
    private String phone;
    private Region region;
    private String macAddress;
    private String carNumber;

}
