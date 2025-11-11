package com.example.apiauth.usecase.service;

import MeshX.common.BaseResponse;
import MeshX.common.UseCase;
import com.example.apiauth.adapter.in.web.dto.StoreInfoResDto;
import com.example.apiauth.adapter.in.web.dto.StoreStateReqDto;
import com.example.apiauth.adapter.in.web.dto.UserReadResDto;
import com.example.apiauth.adapter.out.external.geocoding.dto.GeocodeDto;
import com.example.apiauth.adapter.out.external.shipment.ShipmentClient;
import com.example.apiauth.common.exception.AuthException;
import com.example.apiauth.domain.model.Driver;
import com.example.apiauth.domain.model.Member;
import com.example.apiauth.domain.model.Pos;
import com.example.apiauth.domain.model.Store;
import com.example.apiauth.domain.model.value.MemberRole;
import com.example.apiauth.usecase.port.out.external.GeocodingPort;
import com.example.apiauth.usecase.port.out.persistence.DriverPort;
import com.example.apiauth.usecase.port.out.persistence.MemberPort;
import com.example.apiauth.usecase.port.out.persistence.PosPort;
import com.example.apiauth.usecase.port.out.persistence.StorePort;
import com.example.apiauth.usecase.port.out.usecase.MemberCommandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.apiauth.common.exception.AuthExceptionMessage.*;

@UseCase
@RequiredArgsConstructor
public class MemberCommandService implements MemberCommandUseCase {

    private final MemberPort memberPort;
    private final StorePort storePort;
    private final PosPort posPort;
    private final DriverPort driverPort;
    private final GeocodingPort geocodingPort;
    private final ShipmentClient shipmentClient;

    @Override
    @Transactional
    public void updateStoreInfo(Integer id, StoreInfoResDto dto) {
        Store store = storePort.findByStoreId(id);
        Member member = store.getMember();

        // Member 정보 업데이트
        Member updatedMember = Member.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(dto.getName())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .role(member.getRole())
                .region(dto.getRegion())
                .build();

        memberPort.save(updatedMember);

        // Geocoding으로 좌표 조회
        GeocodeDto geocodeDto = geocodingPort.getCoordinates(dto.getAddress());

        // Store 정보 업데이트
        Store updatedStore = Store.builder()
                .id(store.getId())
                .member(updatedMember)
                .lat(geocodeDto.getLatAsDouble())
                .lon(geocodeDto.getLonAsDouble())
                .storeNumber(dto.getStoreNumber())
                .posCount(store.getPosCount())
                .storeState(store.getStoreState())
                .build();

        storePort.save(updatedStore);
    }

    @Override
    @Transactional
    public void storeStateUpdate(Integer id, StoreStateReqDto dto) {
        Store store = storePort.findByStoreId(id);

        Store updatedStore = Store.builder()
                .id(store.getId())
                .member(store.getMember())
                .lat(store.getLat())
                .lon(store.getLon())
                .storeNumber(store.getStoreNumber())
                .posCount(store.getPosCount())
                .storeState(dto.getStoreState())
                .build();

        storePort.save(updatedStore);
    }

    @Override
    @Transactional
    public void updateUser(Integer id, UserReadResDto dto) {
        Member member = memberPort.findById(id);

        if (!id.equals(dto.getId())) {
            throw new AuthException(ID_MISMATCH);
        }

        // Member 정보 업데이트
        Member updatedMember = Member.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(dto.getName())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .role(member.getRole())
                .region(dto.getRegion())
                .build();

        memberPort.save(updatedMember);

        // Role에 따른 추가 업데이트
        if (member.getRole() == MemberRole.DRIVER) {
            Driver driver = driverPort.findByMember_Id(member.getId());
            Driver updatedDriver = Driver.builder()
                    .id(driver.getId())
                    .member(updatedMember)
                    .macAddress(dto.getMacAddress())
                    .carNumber(driver.getCarNumber())
                    .build();
            driverPort.save(updatedDriver);
        }

        if (member.getRole() == MemberRole.BRANCH_MANAGER) {
            Store store = storePort.findByMemberId(member.getId());

            GeocodeDto geocodeDto = geocodingPort.getCoordinates(dto.getAddress());

            Store updatedStore = Store.builder()
                    .id(store.getId())
                    .member(updatedMember)
                    .lat(geocodeDto.getLatAsDouble())
                    .lon(geocodeDto.getLonAsDouble())
                    .storeNumber(dto.getStoreNumber())
                    .posCount(store.getPosCount())
                    .storeState(store.getStoreState())
                    .build();

            storePort.save(updatedStore);
        }
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        Member member = memberPort.findById(id);

        switch (member.getRole()) {
            case BRANCH_MANAGER:
                Store store = storePort.findByMemberId(member.getId());
                List<Pos> posDevices = posPort.findByStoreIdIn(List.of(store.getId()));
                if (!posDevices.isEmpty()) {
                    throw new AuthException(CANNOT_DELETE_STORE_WITH_POS);
                }
                storePort.delete(store.getId());
                break;

            case POS_MEMBER:
                Pos pos = posPort.findByMember_Id(member.getId());
                Store posStore = pos.getStore();

                // posCount 감소
                Store decreasedStore = posStore.decreasePosCount();
                storePort.save(decreasedStore);

                posPort.delete(pos.getId());
                break;

            case DRIVER:
                Driver driver = driverPort.findByMember_Id(member.getId());

                // Feign으로 진행 중인 배송 확인
                BaseResponse<Boolean> response = shipmentClient.hasActiveShipments(driver.getId());
                if (response.getData() != null && response.getData()) {
                    throw new AuthException(CANNOT_DELETE_DRIVER_WITH_ACTIVE_SHIPMENT);
                }

                driverPort.delete(driver.getId());
                break;

            case ADMIN:
                // 마지막 관리자인지 확인
                long adminCount = memberPort.findAll().stream()
                        .filter(m -> m.getRole() == MemberRole.ADMIN)
                        .count();

                if (adminCount <= 1) {
                    throw new AuthException(CANNOT_DELETE_LAST_ADMIN);
                }
                break;

            case MANAGER:
                break;
        }

        // 사용자 정보 최종 삭제
        memberPort.delete(member.getId());
    }

    @Override
    @Transactional
    public void deleteStore(Integer id) {
        Store store = storePort.findByStoreId(id);

        List<Pos> posDevices = posPort.findByStoreIdIn(List.of(store.getId()));
        for (Pos pos : posDevices) {
            Member posMember = pos.getMember();
            posPort.delete(pos.getId());
            if (posMember != null) {
                memberPort.delete(posMember.getId());
            }
        }

        Member branchManager = store.getMember();
        storePort.delete(store.getId());

        if (branchManager != null) {
            memberPort.delete(branchManager.getId());
        }
    }

    @Override
    @Transactional
    public void deletePos(Integer id) {
        Pos pos = posPort.findByPosId(id);

        Member posMember = pos.getMember();
        posPort.delete(pos.getId());
        if (posMember != null) {
            memberPort.delete(posMember.getId());
        }
    }

    @Override
    @Transactional
    public void deleteDriver(Integer id) {
        Driver driver = driverPort.findByDriverId(id);
        Member driverMember = driver.getMember();

        // Feign으로 진행 중인 배송 확인
        BaseResponse<Boolean> response = shipmentClient.hasActiveShipments(driver.getId());
        if (response.getData() != null && response.getData()) {
            throw new AuthException(CANNOT_DELETE_DRIVER_WITH_ACTIVE_SHIPMENT);
        }

        driverPort.delete(driver.getId());
        if (driverMember != null) {
            memberPort.delete(driverMember.getId());
        }
    }
}
