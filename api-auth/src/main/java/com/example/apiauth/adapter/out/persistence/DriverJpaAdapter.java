package com.example.apiauth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.persistence.entity.DriverEntity;
import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.adapter.out.persistence.mapper.DriverMapper;
import com.example.apiauth.adapter.out.persistence.mapper.MemberMapper;
import com.example.apiauth.common.exception.AuthException;
import com.example.apiauth.common.exception.AuthExceptionMessage;
import com.example.apiauth.domain.model.Driver;
import com.example.apiauth.domain.model.Member;
import com.example.apiauth.usecase.port.out.persistence.DriverPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class DriverJpaAdapter implements DriverPort {

    private final DriverJpaRepository driverJpaRepository;

    @Override
    public List<Driver> findAll() {
        List<DriverEntity> drivers = driverJpaRepository.findAll();

        return drivers.stream()
                .map(DriverMapper::toDomain)
                .toList();
    }


    @Override
    public Driver save(Driver driver) {
        DriverEntity driverEntity = driverJpaRepository.save(DriverMapper.toEntity(driver));
        return DriverMapper.toDomain(driverEntity);
    }

    @Override
    public Driver findByMember_Email(String email) {
        DriverEntity driverEntity = driverJpaRepository.findByMember_Email(email)
                .orElseThrow(() -> new AuthException(AuthExceptionMessage.USER_NAME_NOT_FOUND));

        return DriverMapper.toDomain(driverEntity);
    }

    @Override
    public Driver findByDriverId(Integer driverId) {
        DriverEntity driverEntity = driverJpaRepository.findById(driverId)
                .orElseThrow(() -> new AuthException(AuthExceptionMessage.USER_NAME_NOT_FOUND));

        return DriverMapper.toDomain(driverEntity);
    }

    @Override
    public Driver findByMember(Member member) {
        DriverEntity driverEntity = driverJpaRepository.findByMember(MemberMapper.toEntity(member))
                .orElseThrow(() -> new AuthException(AuthExceptionMessage.USER_NAME_NOT_FOUND));

        return DriverMapper.toDomain(driverEntity);
    }

    @Override
    public Driver findByMember_Id(Integer memberId) {
        DriverEntity driverEntity = driverJpaRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new AuthException(AuthExceptionMessage.USER_NAME_NOT_FOUND));

        return DriverMapper.toDomain(driverEntity);
    }

    @Override
    public void delete(Integer id) {
        driverJpaRepository.deleteById(id);
    }
}
