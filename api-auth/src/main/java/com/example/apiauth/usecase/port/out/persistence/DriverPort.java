package com.example.apiauth.usecase.port.out.persistence;

import com.example.apiauth.domain.model.Driver;
import com.example.apiauth.domain.model.Member;

import java.util.List;

public interface DriverPort {
    Driver findByMember(Member member);
    Driver findByMember_Id(Integer memberId);
    Driver findByDriverId(Integer driverId);
    Driver findByMember_Email(String email);
    Driver save(Driver driver);

    List<Driver> findAll();

    void delete(Integer id);
}
