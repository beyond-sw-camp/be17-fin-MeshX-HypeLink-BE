package org.example.apiauth.adapter.out.persistence;

import org.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberEntity,Integer> {
    Optional<MemberEntity> findByEmail(String email);

}
