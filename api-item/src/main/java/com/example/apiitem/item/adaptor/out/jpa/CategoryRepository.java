package com.example.apiitem.item.adaptor.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByCategory(String category);
    @Modifying
    @Query(value = """
INSERT INTO category_entity (id, category, created_at, updated_at)
VALUES (
  :#{#entity.id},
  :#{#entity.category},
  NOW(),
  NOW()
)
ON DUPLICATE KEY UPDATE
  category = VALUES(category),
  updated_at = NOW()
""", nativeQuery = true)
    void upsert(@Param("entity") CategoryEntity entity);
}
