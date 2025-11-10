package com.example.apiauth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.adapter.out.persistence.entity.StoreEntity;
import com.example.apiauth.adapter.out.persistence.mapper.StoreMapper;
import com.example.apiauth.common.exception.AuthException;
import com.example.apiauth.domain.model.Store;
import com.example.apiauth.usecase.port.out.persistence.StorePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.apiauth.common.exception.AuthExceptionMessage.USER_NAME_NOT_FOUND;

@PersistenceAdapter
@RequiredArgsConstructor
public class StoreJpaAdapter implements StorePort {


    private final StoreJpaRepository storeJpaRepository;

    @Override
    public Store findByMemberId(Integer memberId) {
        StoreEntity storeEntity= storeJpaRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new AuthException(USER_NAME_NOT_FOUND));


        return StoreMapper.toDomain(storeEntity);
    }

    @Override
    public Store findByMember_Email(String memberEmail) {
        StoreEntity storeEntity = storeJpaRepository.findByMember_Email(memberEmail)
                .orElseThrow(() -> new AuthException(USER_NAME_NOT_FOUND));

        return StoreMapper.toDomain(storeEntity);
    }

    @Override
    public Store findByMember(MemberEntity member) {
        return null;
    }

    @Override
    public Store findByStoreId(Integer storeId) {
        return null;
    }

    @Override
    public List<Store> findAllWithMember() {
        return List.of();
    }
}
