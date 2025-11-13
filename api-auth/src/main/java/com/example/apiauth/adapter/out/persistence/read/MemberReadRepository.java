package com.example.apiauth.adapter.out.persistence.read;

import com.example.apiauth.adapter.out.persistence.read.entity.MemberReadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberReadRepository extends JpaRepository<MemberReadEntity, Integer> {

    Optional<MemberReadEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
