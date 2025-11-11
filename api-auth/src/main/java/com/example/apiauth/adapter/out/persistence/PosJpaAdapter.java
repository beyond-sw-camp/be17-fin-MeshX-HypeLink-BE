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
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class PosJpaAdapter implements PosPort {

    private final PosJpaRepository posJpaRepository;

    @Override
    public Pos save(Pos pos) {
        PosEntity posEntity = posJpaRepository.save(PosMapper.toEntity(pos));
        return  PosMapper.toDomain(posEntity);
    }

    @Override
    public List<Pos> findByStoreIdIn(List<Integer> storeIds) {
        List<PosEntity> entities = posJpaRepository.findByStoreIdIn(storeIds);
        return entities.stream()
                .map(PosMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Pos findByMember(MemberEntity member) {
        PosEntity pos = posJpaRepository.findByMember(member)
                .orElseThrow(() -> new AuthException(AuthExceptionMessage.USER_NAME_NOT_FOUND));

        return PosMapper.toDomain(pos);
    }

    @Override
    public Pos findByMember_Id(Integer memberId) {
        PosEntity pos = posJpaRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new AuthException(AuthExceptionMessage.USER_NAME_NOT_FOUND));

        return PosMapper.toDomain(pos);
    }

    @Override
    public Pos findByPosId(Integer posid) {
        PosEntity pos = posJpaRepository.findById(posid)
                .orElseThrow(() -> new AuthException(AuthExceptionMessage.USER_NAME_NOT_FOUND));

        return PosMapper.toDomain(pos);
    }

    @Override
    public Pos findByMember_Email(String email) {
        PosEntity pos = posJpaRepository.findByMember_Email(email)
                .orElseThrow( () -> new AuthException(AuthExceptionMessage.USER_NAME_NOT_FOUND));

        return PosMapper.toDomain(pos);
    }

    @Override
    public void delete(Integer id) {
        posJpaRepository.deleteById(id);
    }
}