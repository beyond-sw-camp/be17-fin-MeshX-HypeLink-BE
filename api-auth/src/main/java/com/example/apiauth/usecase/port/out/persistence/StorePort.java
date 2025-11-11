package com.example.apiauth.usecase.port.out.persistence;

import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.domain.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StorePort {
    Store findByMemberId(Integer memberId);
    Store findByMember(MemberEntity member);
    Store findByMember_Email(String memberEmail);
    Store findByStoreId(Integer storeId);
    List<Store> findAllWithMember();
    Page<Store> findAll(Pageable pageable, String keyWord, String status);

    Store save(Store store);

    void delete(Integer id);
}
