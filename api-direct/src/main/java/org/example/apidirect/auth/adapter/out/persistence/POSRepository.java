package org.example.apidirect.auth.adapter.out.persistence;

import org.example.apidirect.auth.adapter.out.entity.POSEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface POSRepository extends JpaRepository<POSEntity, Integer> {
    Optional<POSEntity> findByPosCode(String posCode);
    Optional<POSEntity> findByMemberId(Integer memberId);
}
