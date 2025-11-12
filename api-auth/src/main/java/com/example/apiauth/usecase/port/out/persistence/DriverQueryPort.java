package com.example.apiauth.usecase.port.out.persistence;

import com.example.apiauth.domain.model.Driver;
import com.example.apiauth.domain.model.Member;

import java.util.List;

public interface DriverQueryPort {
    Driver findById(Integer id);

    List<Driver> findByMemberId(Integer memberId);

    Driver findByMember(Member member);

    Driver findByMember_Id(Integer memberId);

    Driver findByMember_Email(String email);

    List<Driver> findAll();
}
