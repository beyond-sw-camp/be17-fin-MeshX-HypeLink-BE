package MeshX.HypeLink.auth.model.dto.res;

import MeshX.HypeLink.auth.model.entity.MemberRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageUserListResDto {
    private Integer id;
    private String name;
    private MemberRole role;
}
