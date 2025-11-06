package com.example.apiauth.usecase.port.out.persistence;

import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.domain.model.Driver;

public interface DriverPort {
    Driver findByMember(MemberEntity member);
    Driver findByMember_Id(Integer memberId);
    Driver findByDriverId(Integer driverId);
    Driver findByMember_Email(String email);
}
