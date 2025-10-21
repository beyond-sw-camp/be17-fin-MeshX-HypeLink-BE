package MeshX.HypeLink.auth.service;

import MeshX.HypeLink.auth.model.dto.DriverListReqDto;
import MeshX.HypeLink.auth.model.dto.UserListItemDto;
import MeshX.HypeLink.auth.model.dto.UserListResDto;
import MeshX.HypeLink.auth.model.entity.Driver;
import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.MemberRole;
import MeshX.HypeLink.auth.repository.DriverJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.MemberJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepositoryVerify memberJpaRepositoryVerify;
    private final DriverJpaRepositoryVerify driverJpaRepositoryVerify;

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

    public List<DriverListReqDto> dirverList() {
        List<Driver> drivers = driverJpaRepositoryVerify.findAll();

        return drivers.stream()
                .map(driver -> DriverListReqDto.builder()
                        .name(driver.getMember().getName())
                        .phone(driver.getMember().getPhone())
                        .region(driver.getMember().getRegion())
                        .macAddress(driver.getMacAddress())
                        .carNumber(driver.getCarNumber())
                        .build())
                .collect(Collectors.toList());
    }
}
