package com.example.apiauth.usecase.port.out.persistence;

import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.domain.model.Member;
import java.util.Optional;

public interface MemberPort {

    Member findByEmail(String email);

    Member findById(Integer id);

    void save(Member member);

    boolean existsByEmail(String email);
}
