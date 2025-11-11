package com.example.apiauth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.adapter.out.persistence.entity.StoreEntity;
import com.example.apiauth.adapter.out.persistence.mapper.StoreMapper;
import com.example.apiauth.common.exception.AuthException;
import com.example.apiauth.domain.model.Store;
import com.example.apiauth.usecase.port.out.persistence.StorePort;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

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
        StoreEntity storeEntity = storeJpaRepository.findByMember(member)
                .orElseThrow(() -> new AuthException(USER_NAME_NOT_FOUND));

        return StoreMapper.toDomain(storeEntity);
    }

    @Override
    public Store findByStoreId(Integer storeId) {
        StoreEntity storeEntity = storeJpaRepository.findById(storeId)
                .orElseThrow(() -> new AuthException(USER_NAME_NOT_FOUND));
        return StoreMapper.toDomain(storeEntity);
    }

    @Override
    public List<Store> findAllWithMember() {
        List<StoreEntity> entities = storeJpaRepository.findAll();
        return entities.stream()
                .map(StoreMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Store> findAll(Pageable pageable, String keyWord, String status) {
        Page<StoreEntity> entities = storeJpaRepository.findAll(pageable, keyWord, status);
        return entities.map(StoreMapper::toDomain);
    }

    @Override
    public Store save(Store store) {
        StoreEntity storeEntity = storeJpaRepository.save(StoreMapper.toEntity(store));
        return StoreMapper.toDomain(storeEntity);
    }

    @Override
    public void delete(Integer id) {
        storeJpaRepository.deleteById(id);
    }
}
