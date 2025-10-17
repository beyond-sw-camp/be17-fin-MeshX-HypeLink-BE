package MeshX.HypeLink.auth.service;

import MeshX.HypeLink.auth.model.dto.UserListItemDto;
import MeshX.HypeLink.auth.model.dto.UserListResDto;
import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.MemberRole;
import MeshX.HypeLink.auth.repository.MemberJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepositoryVerify memberJpaRepositoryVerify;
    private final StoreJpaRepositoryVerify storeJpaRepositoryVerify;

    public UserListResDto list() {
        List<Member> memberResult = memberJpaRepositoryVerify.findAll();

        return UserListResDto.builder()
                .users(
                        memberResult.stream()
                                .filter(member -> member.getRole() != MemberRole.POS_MEMBER)
                                .map(member -> UserListItemDto.builder()
                                        .name(member.getName())
                                        .email(member.getEmail())
                                        .role(member.getRole())
                                        .region(member.getRegion())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }

}
