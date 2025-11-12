package com.example.apiauth.usecase.port.out.persistence;

import com.example.apiauth.domain.model.Member;

import java.util.List;

public interface MemberQueryPort {

    Member findByEmail(String email);

    Member findById(Integer id);

    boolean existsByEmail(String email);

    List<Member> findAll();
}
