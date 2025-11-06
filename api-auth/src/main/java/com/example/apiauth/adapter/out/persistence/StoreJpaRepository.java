package com.example.apiauth.adapter.out.persistence;

import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.adapter.out.persistence.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StoreJpaRepository extends JpaRepository<StoreEntity, Integer> {

    @Query("select s from StoreEntity s left join fetch s.member")
    List<StoreEntity> findAllWithMember();

    Optional<StoreEntity> findByMember(MemberEntity member);

    Optional<StoreEntity> findByMember_Email(String memberEmail);

    Optional<StoreEntity> findByMember_Id(Integer memberId);
}