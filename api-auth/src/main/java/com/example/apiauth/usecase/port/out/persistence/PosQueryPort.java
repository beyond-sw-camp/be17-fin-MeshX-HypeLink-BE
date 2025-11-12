package com.example.apiauth.usecase.port.out.persistence;

import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.domain.model.Pos;

import java.util.List;

public interface PosQueryPort {
    Pos findById(Integer id);

    List<Pos> findByStoreIdIn(List<Integer> storeIds);

    Pos findByMember(MemberEntity member);

    Pos findByMember_Id(Integer memberId);

    Pos findByMember_Email(String email);

    List<Pos> findAll();
}
