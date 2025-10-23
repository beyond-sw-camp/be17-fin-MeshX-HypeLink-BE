package MeshX.HypeLink.auth.service;

import MeshX.HypeLink.auth.exception.MemberException;
import MeshX.HypeLink.auth.exception.MemberExceptionMessage;
import MeshX.HypeLink.auth.model.PosInfoDto;
import MeshX.HypeLink.auth.model.dto.UserListItemDto;
import MeshX.HypeLink.auth.model.dto.req.DriverListReqDto;
import MeshX.HypeLink.auth.model.dto.req.StoreStateReqDto;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static MeshX.HypeLink.auth.exception.MemberExceptionMessage.ID_MISMATCH;

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
                                        .id(member.getId())
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
                        .id(driver.getMember().getId())
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
                .storeState(store.getStoreState())
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
                .email(store.getMember().getEmail())
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

    public void updateUser(Integer id, UserReadResDto dto) {
        Member member = memberJpaRepositoryVerify.findById(id);

        if (!Objects.equals(id, dto.getId())) {
            throw new MemberException(ID_MISMATCH);
        }

        member.updateName(dto.getName());
        member.updatePhone(dto.getPhone());
        member.updateAddress(dto.getAddress());
        member.updateRegion(dto.getRegion());

        memberJpaRepositoryVerify.save(member);

        if(member.getRole().equals(MemberRole.DRIVER)){
            Driver driver = driverJpaRepositoryVerify.findByMember(member);
            driver.updateMacAddress(dto.getMacAddress());
            driverJpaRepositoryVerify.save(driver);
        }

        if (member.getRole().equals(MemberRole.BRANCH_MANAGER)) {
            Store store = storeJpaRepositoryVerify.findByMember(member);

            GeocodeDto geocodeDto = geocodingService.getCoordinates(dto.getAddress());
            store.updateLat(geocodeDto.getLatAsDouble());
            store.updateLon(geocodeDto.getLonAsDouble());
            store.updateStoreNumber(dto.getStoreNumber());
            storeJpaRepositoryVerify.save(store);

        }
    }

    public void storeStateUpdate(Integer id, StoreStateReqDto dto) {

        Store store = storeJpaRepositoryVerify.findById(id);
        store.updateStoreState(dto.getStoreState());
        storeJpaRepositoryVerify.save(store);
    }

    public UserReadResDto userRead(Integer id) {
        Member member = memberJpaRepositoryVerify.findById(id);

        UserReadResDto.UserReadResDtoBuilder dto = UserReadResDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhone())
                .address(member.getAddress())
                .role(member.getRole())
                .region(member.getRegion());

        if (member.getRole().equals(MemberRole.BRANCH_MANAGER)) {
            Store store = storeJpaRepositoryVerify.findByMember(member);
            dto
                    .storeId(store.getId())
                    .storeNumber(store.getStoreNumber());
        }
        else if (member.getRole().equals(MemberRole.DRIVER)) {
            Driver driver = driverJpaRepositoryVerify.findByMember(member);
            dto
                    .driverId(driver.getId())
                    .macAddress(driver.getMacAddress())
                    .carNumber(driver.getCarNumber());
        }

        return dto.build();
    }

    @Transactional
    public void deleteUser(Integer id) {
        Member member = memberJpaRepositoryVerify.findById(id);

        switch (member.getRole()) {
            case BRANCH_MANAGER:
                Store store = storeJpaRepositoryVerify.findByMember(member);
                List<POS> posDevices = posJpaRepositoryVerify.findByStoreIdIn(List.of(store.getId()));
                if (!posDevices.isEmpty()) {
                    throw new MemberException(MemberExceptionMessage.CANNOT_DELETE_STORE_WITH_POS);
                }
                storeJpaRepositoryVerify.deleteById(store.getId());
                break;

            case POS_MEMBER:
                POS pos = posJpaRepositoryVerify.findByMember(member);
                pos.getStore().decreasePosCount();
                storeJpaRepositoryVerify.save(pos.getStore());
                posJpaRepositoryVerify.deleteById(pos.getId());
                break;

            case DRIVER:
                // TODO: 기사 삭제 시, 할당된 배송 건이 있는지 등 비즈니스 로직 추가 필요
                break;
            case ADMIN:
                memberJpaRepositoryVerify.verifyNotLastAdmin();
                break;
            case MANAGER:
                break;
        }

        // 3. 사용자 정보 최종 삭제
        memberJpaRepositoryVerify.delete(member);
    }

    @Transactional
    public void deleteStore(Integer id) {
        Store store = storeJpaRepositoryVerify.findById(id);

        List<POS> posDevices = posJpaRepositoryVerify.findByStoreIdIn(List.of(store.getId()));
        for (POS pos : posDevices) {
            Member posMember = pos.getMember();
            posJpaRepositoryVerify.deleteById(pos.getId());
            if (posMember != null) {
                memberJpaRepositoryVerify.delete(posMember);
            }
        }

        Member branchManager = store.getMember();
        storeJpaRepositoryVerify.deleteById(store.getId());

        if (branchManager != null) {
            memberJpaRepositoryVerify.delete(store.getMember());
        }
    }

    public void deletePos(Integer id) {
        POS pos = posJpaRepositoryVerify.findById(id);

        Member posMember = pos.getMember();
        posJpaRepositoryVerify.deleteById(pos.getId());
        if (posMember != null) {
            memberJpaRepositoryVerify.delete(posMember);
        }
    }

    public void deleteDriver(Integer id) {
        Member driverMember = memberJpaRepositoryVerify.findById(id);
        Driver driver = driverJpaRepositoryVerify.findByMember(driverMember);

        driverJpaRepositoryVerify.deleteById(driver.getId());
        if (driverMember != null) {
            memberJpaRepositoryVerify.delete(driverMember);
        }

    }
}
