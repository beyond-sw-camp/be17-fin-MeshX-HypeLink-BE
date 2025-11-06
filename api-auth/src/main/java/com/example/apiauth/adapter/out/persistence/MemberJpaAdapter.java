package com.example.apiauth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.adapter.out.persistence.mapper.MemberMapper;
import com.example.apiauth.common.exception.AuthException;
import lombok.RequiredArgsConstructor;
import com.example.apiauth.domain.model.Member;
import com.example.apiauth.usecase.port.out.persistence.MemberPort;

import java.util.Optional;

import static com.example.apiauth.common.exception.AuthExceptionMessage.USER_NAME_NOT_FOUND;

@PersistenceAdapter
@RequiredArgsConstructor
public class MemberJpaAdapter implements MemberPort {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member findByEmail(String email) {
        MemberEntity member = memberJpaRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(USER_NAME_NOT_FOUND));
        return MemberMapper.toDomain(member);
    }

    @Override
    public Member findById(Integer id) {
        MemberEntity member = memberJpaRepository.findById(id)
                .orElseThrow(() -> new AuthException(USER_NAME_NOT_FOUND));

        return MemberMapper.toDomain(member);
    }

    @Override
    public void save(Member member) {
        memberJpaRepository.save(MemberMapper.toEntity(member));
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }
}
