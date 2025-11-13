package org.example.apidirect.item.adapter.out.persistence;

import org.example.apidirect.item.adapter.out.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    Optional<CategoryEntity> findByCategory(String category);

    boolean existsByCategory(String category);

    @Modifying
    @Query(value = """
INSERT INTO store_category (id, category, created_at, updated_at)
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
