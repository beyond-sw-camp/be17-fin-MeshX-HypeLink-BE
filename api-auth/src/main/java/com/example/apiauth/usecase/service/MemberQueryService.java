package com.example.apiauth.usecase.service;

import MeshX.common.UseCase;
import com.example.apiauth.adapter.in.web.dto.*;
import com.example.apiauth.domain.model.Driver;
import com.example.apiauth.domain.model.Member;
import com.example.apiauth.domain.model.Pos;
import com.example.apiauth.domain.model.Store;
import com.example.apiauth.domain.model.value.MemberRole;
import com.example.apiauth.domain.model.value.Region;
import com.example.apiauth.usecase.port.out.persistence.DriverQueryPort;
import com.example.apiauth.usecase.port.out.persistence.MemberQueryPort;
import com.example.apiauth.usecase.port.out.persistence.PosQueryPort;
import com.example.apiauth.usecase.port.out.persistence.StoreQueryPort;
import com.example.apiauth.usecase.port.out.usecase.MemberQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class MemberQueryService implements MemberQueryUseCase {

    private final MemberQueryPort memberQueryPort;
    private final StoreQueryPort storeQueryPort;
    private final PosQueryPort posQueryPort;
    private final DriverQueryPort driverQueryPort;

    @Override
    public Integer getMemberIdByEmail(String email) {
        Member member = memberQueryPort.findByEmail(email);
        return member.getId();
    }
    @Override
    public Integer getStoreIdByEmail(String email) {
        Store store = storeQueryPort.findByMember_Email(email);
        return store.getId();
    }

    @Override
    public Integer getPosIdByEmail(String email) {
        Pos pos = posQueryPort.findByMember_Email(email);
        return pos.getId();
    }

    @Override
    public Integer getDriverIdByEmail(String email) {
        Driver driver = driverQueryPort.findByMember_Email(email);
        return driver.getId();
    }

    @Override
    public List<MemberListNotPosResDto> memberlistNotPos() {
        List<Member> members = memberQueryPort.findAll();

        return members.stream()
                .filter(member -> member.getRole() != MemberRole.POS_MEMBER)
                .map(member -> MemberListNotPosResDto.builder()
                        .id(member.getId())
                        .name(member.getName())
                        .email(member.getEmail())
                        .role(member.getRole())
                        .region(member.getRegion())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<DriverListResDto> dirverList() {
        List<Driver> drivers = driverQueryPort.findAll();

        return drivers.stream()
                .map(driver -> {
                    Member member = driver.getMember();
                    return DriverListResDto.builder()
                            .id(driver.getId())
                            .name(member != null ? member.getName() : "동기화 중")
                            .phone(member != null ? member.getPhone() : "동기화 중")
                            .region(member != null ? member.getRegion() : null)
                            .macAddress(driver.getMacAddress())
                            .carNumber(driver.getCarNumber())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<StoreWithPosResDto> storeWithPosList() {
        List<Store> stores = storeQueryPort.findAllWithMember();

        List<Integer> storeIds = stores.stream()
                .map(Store::getId)
                .collect(Collectors.toList());

        List<Pos> allPos = posQueryPort.findByStoreIdIn(storeIds);

        Map<Integer, List<Pos>> posGroupByStore = allPos.stream()
                .collect(Collectors.groupingBy(pos -> pos.getStore().getId()));

        return stores.stream()
                .map(store -> createStoreWithPosResDto(store, posGroupByStore.getOrDefault(store.getId(), List.of())))
                .collect(Collectors.toList());
    }

    private StoreWithPosResDto createStoreWithPosResDto(Store store, List<Pos> posList) {
        Member storeMember = store.getMember();
        return StoreWithPosResDto.builder()
                .id(store.getId())
                .name(storeMember != null ? storeMember.getName() : "동기화 중")
                .address(storeMember != null ? storeMember.getAddress() : "동기화 중")
                .region(storeMember != null ? storeMember.getRegion() : null)
                .storeState(store.getStoreState())
                .posDevices(posList.stream()
                        .map(pos -> {
                            Member posMember = pos.getMember();
                            return PosInfoDto.builder()
                                    .id(pos.getId())
                                    .name(posMember != null ? posMember.getName() : "동기화 중")
                                    .email(posMember != null ? posMember.getEmail() : "동기화 중")
                                    .posCode(pos.getPosCode())
                                    .build();
                        })
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public Page<StoreListResDto> storeList(Pageable pageable, String keyWord, String status) {
        Page<Store> stores = storeQueryPort.findAll(pageable, keyWord, status);

        return stores.map(store -> {
            Member member = store.getMember();
            return StoreListResDto.builder()
                    .storeId(store.getId())
                    .storeName(member != null ? member.getName() : "동기화 중")
                    .storeAddress(member != null ? member.getAddress() : "동기화 중")
                    .storePhone(member != null ? member.getPhone() : "동기화 중")
                    .storeState(store.getStoreState())
                    .build();
        });
    }

    @Override
    public StoreWithPosResDto readMyStore(Integer memberId) {
        // memberId로 Member 조회
        Member member = memberQueryPort.findById(memberId);

        Store store;
        // POS_MEMBER인 경우: POS 테이블에서 member로 조회 → Store 찾기
        if (member.getRole() == MemberRole.POS_MEMBER) {
            Pos pos = posQueryPort.findByMember_Id(memberId);
            store = pos.getStore();
        } else {
            // BRANCH_MANAGER 등 Store owner인 경우
            store = storeQueryPort.findByMemberId(memberId);
        }

        List<Pos> posList = posQueryPort.findByStoreIdIn(List.of(store.getId()));

        return createStoreWithPosResDto(store, posList);
    }

    @Override
    public StoreWithPosResDto readOtherStroe(Integer id) {
        Store store = storeQueryPort.findById(id);
        List<Pos> posList = posQueryPort.findByStoreIdIn(List.of(id));

        return createStoreWithPosResDto(store, posList);
    }

    @Override
    public StoreInfoResDto readStoreInfo(Integer id) {
        Store store = storeQueryPort.findById(id);
        Member member = store.getMember();

        return StoreInfoResDto.builder()
                .name(member != null ? member.getName() : "동기화 중")
                .email(member != null ? member.getEmail() : "동기화 중")
                .phone(member != null ? member.getPhone() : "동기화 중")
                .address(member != null ? member.getAddress() : "동기화 중")
                .region(member != null ? member.getRegion() : null)
                .storeNumber(store.getStoreNumber())
                .build();
    }

    @Override
    public UserReadResDto userRead(Integer id) {
        Member member = memberQueryPort.findById(id);

        UserReadResDto.UserReadResDtoBuilder dto = UserReadResDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhone())
                .address(member.getAddress())
                .role(member.getRole())
                .region(member.getRegion());

        if (member.getRole() == MemberRole.BRANCH_MANAGER) {
            Store store = storeQueryPort.findByMemberId(member.getId());
            dto.storeId(store.getId())
               .storeNumber(store.getStoreNumber());
        } else if (member.getRole() == MemberRole.DRIVER) {
            Driver driver = driverQueryPort.findByMember_Id(member.getId());
            dto.driverId(driver.getId())
               .macAddress(driver.getMacAddress())
               .carNumber(driver.getCarNumber());
        }

        return dto.build();
    }

    @Override
    public StoreAddInfoListRes getStoreAddress() {
        List<Store> stores = storeQueryPort.findAll();

        return StoreAddInfoListRes.toDto(stores);
    }

}
