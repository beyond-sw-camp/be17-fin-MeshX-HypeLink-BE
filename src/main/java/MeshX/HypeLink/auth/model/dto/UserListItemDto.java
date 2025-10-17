package MeshX.HypeLink.auth.model.dto;

import MeshX.HypeLink.auth.model.entity.MemberRole;
import MeshX.HypeLink.auth.model.entity.Region;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserListItemDto {
    private String name;
    private String email;
    private MemberRole role;
    private Region region;
}