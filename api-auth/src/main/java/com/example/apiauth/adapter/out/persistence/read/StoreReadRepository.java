package com.example.apiauth.adapter.out.persistence.read;

import com.example.apiauth.adapter.out.persistence.read.entity.StoreReadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreReadRepository extends JpaRepository<StoreReadEntity, Integer> {

    @Query("SELECT s FROM StoreReadEntity s JOIN FETCH s.member WHERE s.id = :id")
    Optional<StoreReadEntity> findByIdWithMember(@Param("id") Integer id);

    @Query("SELECT s FROM StoreReadEntity s JOIN FETCH s.member WHERE s.member.id = :memberId")
    List<StoreReadEntity> findByMemberIdWithMember(@Param("memberId") Integer memberId);

    @Query("SELECT s FROM StoreReadEntity s JOIN FETCH s.member")
    List<StoreReadEntity> findAllWithMember();
}
