package com.example.apiauth.adapter.out.persistence;

import com.example.apiauth.adapter.out.persistence.entity.DriverEntity;
import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.domain.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverJpaRepository extends JpaRepository<DriverEntity, Integer> {
    Optional<DriverEntity> findByMember(MemberEntity member);
    Optional<DriverEntity> findByMember_Id(Integer memberId);
    Optional<DriverEntity> findByMember_Email(String email);
}
