package com.example.apiauth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.adapter.out.persistence.mapper.MemberMapper;
import com.example.apiauth.common.exception.AuthException;
import lombok.RequiredArgsConstructor;
import com.example.apiauth.domain.model.Member;
import com.example.apiauth.usecase.port.out.persistence.MemberPort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.apiauth.common.exception.AuthExceptionMessage.USER_NAME_NOT_FOUND;

@PersistenceAdapter
@RequiredArgsConstructor
public class MemberJpaAdapter implements MemberPort {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public List<Member> findAll() {
        List<MemberEntity> memberEntities = memberJpaRepository.findAll();

        return memberEntities.stream()
                .map(MemberMapper::toDomain)
                .toList();
    }


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
    public Member save(Member member) {
        MemberEntity memberEntity = memberJpaRepository.save(MemberMapper.toEntity(member));
        return  MemberMapper.toDomain(memberEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return memberJpaRepository.existsByEmail(email);
    }

    @Override
    public void delete(Integer id) {
        memberJpaRepository.deleteById(id);
    }
}
