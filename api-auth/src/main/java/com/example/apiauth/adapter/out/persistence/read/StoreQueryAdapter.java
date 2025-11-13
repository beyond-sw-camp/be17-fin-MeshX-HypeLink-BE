package com.example.apiauth.adapter.out.persistence.read;

import MeshX.common.PersistenceAdapter;
import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.adapter.out.persistence.read.entity.StoreReadEntity;
import com.example.apiauth.adapter.out.persistence.read.mapper.StoreReadMapper;
import com.example.apiauth.common.exception.AuthException;
import com.example.apiauth.domain.model.Store;
import com.example.apiauth.usecase.port.out.persistence.StoreQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.apiauth.common.exception.AuthExceptionMessage.STORE_NOT_FOUND;

@PersistenceAdapter
@RequiredArgsConstructor
public class StoreQueryAdapter implements StoreQueryPort {

    private final StoreReadJpaRepository storeReadJpaRepository;

    @Override
    public Store findById(Integer id) {
        StoreReadEntity storeEntity = storeReadJpaRepository.findByIdWithMember(id)
                .orElseThrow(() -> new AuthException(STORE_NOT_FOUND));
        return StoreReadMapper.toDomain(storeEntity);
    }

    @Override
    public Store findByMemberId(Integer memberId) {
        List<StoreReadEntity> stores = storeReadJpaRepository.findByMemberIdWithMember(memberId);
        if (stores.isEmpty()) {
            throw new AuthException(STORE_NOT_FOUND);
        }
        return StoreReadMapper.toDomain(stores.get(0));
    }

    @Override
    public Store findByMember(MemberEntity member) {
        if (member == null || member.getId() == null) {
            throw new AuthException(STORE_NOT_FOUND);
        }
        return findByMemberId(member.getId());
    }

    @Override
    public Store findByMember_Email(String memberEmail) {
        // MemberReadRepository에 email로 조회하는 메서드 필요
        throw new UnsupportedOperationException("findByMember_Email not yet implemented");
    }

    @Override
    public List<Store> findAll() {
        return storeReadJpaRepository.findAllWithMember().stream()
                .map(StoreReadMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Store> findAllWithMember() {
        return findAll(); // 이미 Member를 JOIN FETCH하므로 동일
    }

    @Override
    public Page<Store> findAll(Pageable pageable, String keyWord, String status) {
        Page<StoreReadEntity> entityPage = storeReadJpaRepository.findAll(pageable, keyWord, status);
        return entityPage.map(StoreReadMapper::toDomain);
    }
}
