package MeshX.HypeLink.auth.model.dto.sync;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.MemberRole;
import MeshX.HypeLink.auth.model.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSyncDto {
    private Integer id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;
    private MemberRole role;
    private Region region;
    private String refreshToken;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MemberSyncDto from(Member member) {
        return MemberSyncDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .phone(member.getPhone())
                .address(member.getAddress())
                .role(member.getRole())
                .region(member.getRegion())
                .refreshToken(member.getRefreshToken())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
}
