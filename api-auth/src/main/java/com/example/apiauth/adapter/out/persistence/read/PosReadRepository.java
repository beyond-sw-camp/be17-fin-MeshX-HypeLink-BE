package com.example.apiauth.adapter.out.persistence.read;

import com.example.apiauth.adapter.out.persistence.read.entity.PosReadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PosReadRepository extends JpaRepository<PosReadEntity, Integer> {

    @Query("SELECT p FROM PosReadEntity p JOIN FETCH p.store s JOIN FETCH p.member WHERE p.id = :id")
    Optional<PosReadEntity> findByIdWithAll(@Param("id") Integer id);

    @Query("SELECT p FROM PosReadEntity p JOIN FETCH p.store JOIN FETCH p.member WHERE p.store.id = :storeId")
    List<PosReadEntity> findByStoreIdWithAll(@Param("storeId") Integer storeId);

    @Query("SELECT p FROM PosReadEntity p JOIN FETCH p.store JOIN FETCH p.member WHERE p.member.id = :memberId")
    Optional<PosReadEntity> findByMemberIdWithAll(@Param("memberId") Integer memberId);

    @Query("SELECT p FROM PosReadEntity p JOIN FETCH p.store JOIN FETCH p.member")
    List<PosReadEntity> findAllWithAll();
}
