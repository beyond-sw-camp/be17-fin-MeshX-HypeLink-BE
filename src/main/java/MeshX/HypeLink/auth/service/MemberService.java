package MeshX.HypeLink.auth.service;

import MeshX.HypeLink.auth.model.PosInfoDto;
import MeshX.HypeLink.auth.model.dto.UserListItemDto;
import MeshX.HypeLink.auth.model.dto.req.DriverListReqDto;
import MeshX.HypeLink.auth.model.dto.req.StoreWithPosListReqDto;
import MeshX.HypeLink.auth.model.dto.res.UserListResDto;
import MeshX.HypeLink.auth.model.entity.*;
import MeshX.HypeLink.auth.repository.DriverJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.MemberJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.PosJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepositoryVerify memberJpaRepositoryVerify;
    private final DriverJpaRepositoryVerify driverJpaRepositoryVerify;
    private final StoreJpaRepositoryVerify storeJpaRepositoryVerify;
    private final PosJpaRepositoryVerify posJpaRepositoryVerify;

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

    public List<StoreWithPosListReqDto> storeList() {
        List<Store> stores = storeJpaRepositoryVerify.findAll();

        List<Integer> storeIds = stores.stream()
                .map(Store::getId)
                .collect(Collectors.toList());

        List<POS> allPos = posJpaRepositoryVerify.findByStoreIdIn(storeIds);

        Map<Integer, List<POS>> posGroupByStore = allPos.stream()
                .collect(Collectors.groupingBy(pos -> pos.getStore().getId()));

        return stores.stream()
                .map(store -> new StoreWithPosListReqDto(
                        store.getId(),
                        store.getMember().getName(),
                        posGroupByStore.getOrDefault(store.getId(), Collections.emptyList())
                                .stream()
                                .map(pos -> PosInfoDto.builder()
                                        .id(pos.getId())
                                        .name(pos.getMember().getName())
                                        .posCode(pos.getPosCode())
                                        .build())
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}
