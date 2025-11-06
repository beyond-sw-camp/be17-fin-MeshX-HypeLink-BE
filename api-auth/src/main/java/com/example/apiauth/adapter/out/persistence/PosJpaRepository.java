package com.example.apiauth.adapter.out.persistence;

import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.adapter.out.persistence.entity.PosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PosJpaRepository extends JpaRepository<PosEntity, Integer> {
    @Query("SELECT p FROM PosEntity p " +
            "LEFT JOIN FETCH p.member " +
            "WHERE p.store.id IN :storeIds")
    List<PosEntity> findByStoreIdIn(@Param("storeIds") List<Integer> storeIds);

    Optional<PosEntity> findByMember(MemberEntity member);

    Optional<PosEntity> findByMember_Id(Integer memberId);

    Optional<PosEntity> findByMember_Email(String email);

}
