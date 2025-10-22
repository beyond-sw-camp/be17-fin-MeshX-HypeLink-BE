package MeshX.HypeLink.auth.model.dto.res;

import MeshX.HypeLink.auth.model.dto.UserListItemDto;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class UserListResDto {
    private List<UserListItemDto> users;
}