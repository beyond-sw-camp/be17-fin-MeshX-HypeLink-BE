package com.example.apiauth.usecase.port.out.persistence;

import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.domain.model.Pos;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PosPort {

    List<Pos> findByStoreIdIn(List<Integer> storeIds);

    Pos findByMember(MemberEntity member);

    Pos findByMember_Id(Integer memberId);

    Pos findByPosId(Integer posid);

    Pos findByMember_Email(String email);
}
