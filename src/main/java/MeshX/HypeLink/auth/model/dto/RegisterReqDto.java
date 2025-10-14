package MeshX.HypeLink.auth.model.dto;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.MemberRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterReqDto {
    private String email;
    private String password;
    private String name;
    private MemberRole role;

    // 추후 지점 추가 예정

    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .email(this.email)
                .password(encodedPassword)
                .name(this.name)
                .role(this.role)
                .build();
    }
}
