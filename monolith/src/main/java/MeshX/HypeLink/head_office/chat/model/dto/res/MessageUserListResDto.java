package MeshX.HypeLink.head_office.chat.model.dto.res;

import MeshX.HypeLink.auth.model.entity.MemberRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageUserListResDto {
    private Integer id;
    private String email;
    private String name;
    private MemberRole role;
    private Integer unreadCount;
}
