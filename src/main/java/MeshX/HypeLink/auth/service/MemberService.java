package MeshX.HypeLink.auth.service;

import MeshX.HypeLink.auth.model.PosInfoDto;
import MeshX.HypeLink.auth.model.dto.UserListItemDto;
import MeshX.HypeLink.auth.model.dto.req.DriverListReqDto;
import MeshX.HypeLink.auth.model.dto.res.*;
import MeshX.HypeLink.auth.model.entity.*;
import MeshX.HypeLink.auth.repository.DriverJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.MemberJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.PosJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import MeshX.HypeLink.utils.geocode.model.dto.GeocodeDto;
import MeshX.HypeLink.utils.geocode.service.GeocodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepositoryVerify memberJpaRepositoryVerify;
    private final DriverJpaRepositoryVerify driverJpaRepositoryVerify;
    private final StoreJpaRepositoryVerify storeJpaRepositoryVerify;
    private final PosJpaRepositoryVerify posJpaRepositoryVerify;
    private final GeocodingService geocodingService;


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

    public List<StoreWithPosResDto> storeWithPosList() {
        List<Store> stores = storeJpaRepositoryVerify.findAll();

        List<Integer> storeIds = stores.stream()
                .map(Store::getId)
                .collect(Collectors.toList());

        List<POS> allPos = posJpaRepositoryVerify.findByStoreIdIn(storeIds);

        Map<Integer, List<POS>> posGroupByStore = allPos.stream()
                .collect(Collectors.groupingBy(pos -> pos.getStore().getId()));

        return stores.stream()
                .map(store -> getStoreWithPosResDto(store, posGroupByStore))
                .collect(Collectors.toList());
    }

    public List<StoreListResDto> storeList() {
        List<Store> stores = storeJpaRepositoryVerify.findAll();

        return stores.stream()
                .map(store -> StoreListResDto.builder()
                        .storeId(store.getId())
                        .storeName(store.getMember().getName())
                        .storeAddress(store.getMember().getAddress())
                        .storePhone(store.getMember().getPhone())
                        .storeState(store.getStoreState())
                        .build()).toList();
    }

    public List<MessageUserListResDto> messageUserList() {
        List<Member> memberResult = memberJpaRepositoryVerify.findAll();

        return memberResult.stream()
                .filter(member -> ALLOWED_ROLES.contains(member.getRole()))
                .map(member -> MessageUserListResDto.builder()
                        .id(member.getId())
                        .name(member.getName())
                        .role(member.getRole())
                        .build()).toList();
    }

    private static final Set<MemberRole> ALLOWED_ROLES = Set.of(
            MemberRole.ADMIN,
            MemberRole.MANAGER,
            MemberRole.BRANCH_MANAGER
    );

    public StoreWithPosResDto readMyStore(Member member) {
        Store store = storeJpaRepositoryVerify.findByMember(member);
        List<POS> pos = posJpaRepositoryVerify.findByStoreIdIn(List.of(store.getId()));

        return getStoreWithPosResDto(store, pos);
    }


    public StoreWithPosResDto readOtherStroe(Integer id) {
        Store store = storeJpaRepositoryVerify.findById(id);
        List<POS> pos = posJpaRepositoryVerify.findByStoreIdIn(List.of(id));

        return getStoreWithPosResDto(store, pos);

    }

    private StoreWithPosResDto getStoreWithPosResDto(Store store, List<POS> pos) {
        return StoreWithPosResDto.builder()
                .id(store.getId())
                .name(store.getMember().getName())
                .address(store.getMember().getAddress())
                .region(store.getMember().getRegion())
                .posDevices(
                        pos.stream()
                                .map(p -> PosInfoDto.builder()
                                        .id(p.getId())
                                        .name(p.getMember().getName())
                                        .email(p.getMember().getEmail())
                                        .posCode(p.getPosCode())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }

    private StoreWithPosResDto getStoreWithPosResDto(Store store, Map<Integer, List<POS>> posGroupByStore) {
        List<POS> pos = posGroupByStore.getOrDefault(store.getId(), Collections.emptyList());
        return getStoreWithPosResDto(store, pos);
    }

    public Member findMember(String username) {
        return memberJpaRepositoryVerify.findByEmail(username);
    }

    public StoreInfoResDto readStoreInfo(Integer id) {
        Store store = storeJpaRepositoryVerify.findById(id);
        return StoreInfoResDto.builder()
                .name(store.getMember().getName())
                .phone(store.getMember().getPhone())
                .address(store.getMember().getAddress())
                .region(store.getMember().getRegion())
                .storeNumber(store.getStoreNumber())
                .build();
    }

    public void updateStoreInfo(Integer id, StoreInfoResDto dto) {
        Store store = storeJpaRepositoryVerify.findById(id);
        Member member = store.getMember();

        member.updateName(dto.getName());
        member.updatePhone(dto.getPhone());
        member.updateAddress(dto.getAddress());
        member.updateRegion(dto.getRegion());

        GeocodeDto geocodeDto = geocodingService.getCoordinates(dto.getAddress());
        store.updateLat(geocodeDto.getLatAsDouble());
        store.updateLon(geocodeDto.getLonAsDouble());
        store.updateStoreNumber(dto.getStoreNumber());
        memberJpaRepositoryVerify.save(member);
        storeJpaRepositoryVerify.save(store);
    }
}
