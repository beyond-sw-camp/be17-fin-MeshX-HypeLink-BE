package com.example.apiitem.item.adaptor.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SizeRepository extends JpaRepository<SizeEntity, Integer> {
    Optional<SizeEntity> findBySize(String size);

    @Modifying
    @Query(value = """
INSERT INTO size_entity (id, size, created_at, updated_at)
VALUES (
  :#{#entity.id},
  :#{#entity.size},
  NOW(),
  NOW()
)
ON DUPLICATE KEY UPDATE
  size = VALUES(size),
  updated_at = NOW()
""", nativeQuery = true)
    void upsert(@Param("entity") SizeEntity entity);
}
