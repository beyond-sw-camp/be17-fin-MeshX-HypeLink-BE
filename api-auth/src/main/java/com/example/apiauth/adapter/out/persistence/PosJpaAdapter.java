package com.example.apiauth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.adapter.out.persistence.entity.PosEntity;
import com.example.apiauth.adapter.out.persistence.mapper.PosMapper;
import com.example.apiauth.common.exception.AuthException;
import com.example.apiauth.common.exception.AuthExceptionMessage;
import com.example.apiauth.domain.model.Pos;
import com.example.apiauth.usecase.port.out.persistence.PosPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class PosJpaAdapter implements PosPort {

    private final PosJpaRepository posJpaRepository;

    @Override
    public List<Pos> findByStoreIdIn(List<Integer> storeIds) {
        return List.of();
    }

    @Override
    public Pos findByMember(MemberEntity member) {
        return null;
    }

    @Override
    public Pos findByMember_Id(Integer memberId) {
        return null;
    }

    @Override
    public Pos findByPosId(Integer posid) {
        return null;
    }

    @Override
    public Pos findByMember_Email(String email) {
        PosEntity pos = posJpaRepository.findByMember_Email(email)
                .orElseThrow( () -> new AuthException(AuthExceptionMessage.USER_NAME_NOT_FOUND));

        return PosMapper.toDomain(pos);
    }
}