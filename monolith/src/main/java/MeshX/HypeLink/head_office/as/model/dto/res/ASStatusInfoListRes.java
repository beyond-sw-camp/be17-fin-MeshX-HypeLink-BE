package MeshX.HypeLink.head_office.as.model.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ASStatusInfoListRes {
    private List<ASStatusInfoRes> status;
}
