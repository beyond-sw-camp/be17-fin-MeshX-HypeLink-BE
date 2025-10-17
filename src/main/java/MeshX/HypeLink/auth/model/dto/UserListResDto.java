package MeshX.HypeLink.auth.model.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class UserListResDto {
    private List<UserListItemDto> users;
}