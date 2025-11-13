package com.example.apiauth.adapter.out.persistence.read;

import com.example.apiauth.adapter.out.persistence.read.entity.DriverReadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DriverReadRepository extends JpaRepository<DriverReadEntity, Integer> {

    @Query("SELECT d FROM DriverReadEntity d JOIN FETCH d.member WHERE d.id = :id")
    Optional<DriverReadEntity> findByIdWithMember(@Param("id") Integer id);

    @Query("SELECT d FROM DriverReadEntity d JOIN FETCH d.member WHERE d.member.id = :memberId")
    List<DriverReadEntity> findByMemberIdWithMember(@Param("memberId") Integer memberId);

    @Query("SELECT d FROM DriverReadEntity d JOIN FETCH d.member")
    List<DriverReadEntity> findAllWithMember();
}
