package com.example.apiauth.adapter.out.persistence.read;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.persistence.read.entity.MemberReadEntity;
import com.example.apiauth.adapter.out.persistence.read.mapper.MemberReadMapper;
import com.example.apiauth.common.exception.AuthException;
import com.example.apiauth.domain.model.Member;
import com.example.apiauth.usecase.port.out.persistence.MemberQueryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.apiauth.common.exception.AuthExceptionMessage.USER_NAME_NOT_FOUND;

@PersistenceAdapter
@RequiredArgsConstructor
public class MemberQueryAdapter implements MemberQueryPort {

    private final MemberReadRepository memberReadRepository;

    @Override
    public Member findByEmail(String email) {
        MemberReadEntity memberEntity = memberReadRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(USER_NAME_NOT_FOUND));
        return MemberReadMapper.toDomain(memberEntity);
    }

    @Override
    public Member findById(Integer id) {
        MemberReadEntity memberEntity = memberReadRepository.findById(id)
                .orElseThrow(() -> new AuthException(USER_NAME_NOT_FOUND));
        return MemberReadMapper.toDomain(memberEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return memberReadRepository.existsByEmail(email);
    }

    @Override
    public List<Member> findAll() {
        return memberReadRepository.findAll().stream()
                .map(MemberReadMapper::toDomain)
                .collect(Collectors.toList());
    }
}
