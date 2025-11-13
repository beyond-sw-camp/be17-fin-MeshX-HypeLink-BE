package com.example.apiauth.usecase.port.out.persistence;

import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.domain.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreQueryPort {
    Store findById(Integer id);

    Store findByMemberId(Integer memberId);

    Store findByMember(MemberEntity member);

    Store findByMember_Email(String memberEmail);

    List<Store> findAll();

    List<Store> findAllWithMember();

    Page<Store> findAll(Pageable pageable, String keyWord, String status);
}
