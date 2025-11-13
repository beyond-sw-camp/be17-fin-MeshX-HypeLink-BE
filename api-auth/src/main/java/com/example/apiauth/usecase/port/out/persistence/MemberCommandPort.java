package com.example.apiauth.usecase.port.out.persistence;

import com.example.apiauth.domain.model.Member;

public interface MemberCommandPort {

    Member save(Member member);

    void delete(Integer id);
}
