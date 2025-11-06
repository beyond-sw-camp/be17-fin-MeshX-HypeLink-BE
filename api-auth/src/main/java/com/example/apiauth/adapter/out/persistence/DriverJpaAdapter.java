package com.example.apiauth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.persistence.entity.DriverEntity;
import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.adapter.out.persistence.mapper.DriverMapper;
import com.example.apiauth.common.exception.AuthException;
import com.example.apiauth.common.exception.AuthExceptionMessage;
import com.example.apiauth.domain.model.Driver;
import com.example.apiauth.usecase.port.out.persistence.DriverPort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class DriverJpaAdapter implements DriverPort {

    private final DriverJpaRepository driverJpaRepository;

    @Override
    public Driver findByMember_Email(String email) {
        DriverEntity driverEntity = driverJpaRepository.findByMember_Email(email)
                .orElseThrow(() -> new AuthException(AuthExceptionMessage.USER_NAME_NOT_FOUND));

        return DriverMapper.toDomain(driverEntity);
    }

    @Override
    public Driver findByDriverId(Integer driverId) {
        return null;
    }

    @Override
    public Driver findByMember(MemberEntity member) {
        return null;
    }

    @Override
    public Driver findByMember_Id(Integer memberId) {
        return null;
    }
}
