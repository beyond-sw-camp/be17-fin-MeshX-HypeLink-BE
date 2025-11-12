package com.example.apiauth.adapter.out.persistence.read;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.adapter.out.persistence.read.entity.PosReadEntity;
import com.example.apiauth.adapter.out.persistence.read.mapper.PosReadMapper;
import com.example.apiauth.common.exception.AuthException;
import com.example.apiauth.domain.model.Pos;
import com.example.apiauth.usecase.port.out.persistence.PosQueryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.apiauth.common.exception.AuthExceptionMessage.POS_NOT_FOUND;

@PersistenceAdapter
@RequiredArgsConstructor
public class PosQueryAdapter implements PosQueryPort {

    private final PosReadRepository posReadRepository;

    @Override
    public Pos findById(Integer id) {
        PosReadEntity posEntity = posReadRepository.findByIdWithAll(id)
                .orElseThrow(() -> new AuthException(POS_NOT_FOUND));
        return PosReadMapper.toDomain(posEntity);
    }

    @Override
    public List<Pos> findByStoreIdIn(List<Integer> storeIds) {
        // Repository에 메서드 필요 - 임시로 하나씩 조회
        return storeIds.stream()
                .flatMap(storeId -> posReadRepository.findByStoreIdWithAll(storeId).stream())
                .map(PosReadMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Pos findByMember(MemberEntity member) {
        if (member == null || member.getId() == null) {
            throw new AuthException(POS_NOT_FOUND);
        }
        return findByMember_Id(member.getId());
    }

    @Override
    public Pos findByMember_Id(Integer memberId) {
        PosReadEntity posEntity = posReadRepository.findByMemberIdWithAll(memberId)
                .orElseThrow(() -> new AuthException(POS_NOT_FOUND));
        return PosReadMapper.toDomain(posEntity);
    }

    @Override
    public Pos findByMember_Email(String email) {
        // MemberReadRepository에 email로 조회하는 메서드 필요
        throw new UnsupportedOperationException("findByMember_Email not yet implemented");
    }

    @Override
    public List<Pos> findAll() {
        return posReadRepository.findAllWithAll().stream()
                .map(PosReadMapper::toDomain)
                .collect(Collectors.toList());
    }
}
