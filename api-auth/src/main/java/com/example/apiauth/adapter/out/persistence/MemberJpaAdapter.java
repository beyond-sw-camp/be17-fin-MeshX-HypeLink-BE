package com.example.apiauth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import com.example.apiauth.domain.model.Member;
import com.example.apiauth.usecase.port.out.persistence.MemberPort;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class MemberJpaAdapter implements MemberPort {

    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Member save(Member member) {
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }
}
