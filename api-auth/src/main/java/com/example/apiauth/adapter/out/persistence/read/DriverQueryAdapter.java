package com.example.apiauth.adapter.out.persistence.read;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.persistence.read.entity.DriverReadEntity;
import com.example.apiauth.adapter.out.persistence.read.mapper.DriverReadMapper;
import com.example.apiauth.common.exception.AuthException;
import com.example.apiauth.domain.model.Driver;
import com.example.apiauth.domain.model.Member;
import com.example.apiauth.usecase.port.out.persistence.DriverQueryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.apiauth.common.exception.AuthExceptionMessage.DRIVER_NOT_FOUND;

@PersistenceAdapter
@RequiredArgsConstructor
public class DriverQueryAdapter implements DriverQueryPort {

    private final DriverReadRepository driverReadRepository;

    @Override
    public Driver findById(Integer id) {
        DriverReadEntity driverEntity = driverReadRepository.findByIdWithMember(id)
                .orElseThrow(() -> new AuthException(DRIVER_NOT_FOUND));
        return DriverReadMapper.toDomain(driverEntity);
    }

    @Override
    public List<Driver> findByMemberId(Integer memberId) {
        return driverReadRepository.findByMemberIdWithMember(memberId).stream()
                .map(DriverReadMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Driver findByMember(Member member) {
        if (member == null || member.getId() == null) {
            throw new AuthException(DRIVER_NOT_FOUND);
        }
        List<Driver> drivers = findByMemberId(member.getId());
        if (drivers.isEmpty()) {
            throw new AuthException(DRIVER_NOT_FOUND);
        }
        return drivers.get(0);
    }

    @Override
    public Driver findByMember_Id(Integer memberId) {
        List<Driver> drivers = findByMemberId(memberId);
        if (drivers.isEmpty()) {
            throw new AuthException(DRIVER_NOT_FOUND);
        }
        return drivers.get(0);
    }

    @Override
    public Driver findByMember_Email(String email) {
        // MemberReadRepository에 email로 조회하는 메서드 필요
        // 임시로 예외 처리
        throw new UnsupportedOperationException("findByMember_Email not yet implemented");
    }

    @Override
    public List<Driver> findAll() {
        return driverReadRepository.findAllWithMember().stream()
                .map(DriverReadMapper::toDomain)
                .collect(Collectors.toList());
    }
}
