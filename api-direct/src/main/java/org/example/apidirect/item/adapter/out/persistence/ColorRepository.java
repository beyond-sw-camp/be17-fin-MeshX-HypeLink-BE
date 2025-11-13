package org.example.apidirect.item.adapter.out.persistence;

import org.example.apidirect.item.adapter.out.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<ColorEntity, Integer> {
    Optional<ColorEntity> findByColorName(String colorName);

    @Modifying
    @Query(value = """
INSERT INTO color (id, color_name, color_code, created_at, updated_at)
VALUES (
  :#{#entity.id},
  :#{#entity.colorName},
  :#{#entity.colorCode},
  NOW(),
  NOW()
)
ON DUPLICATE KEY UPDATE
  color_name = VALUES(color_name),
  color_code = VALUES(color_code),
  updated_at = NOW()
""", nativeQuery = true)
    void upsert(@Param("entity") ColorEntity entity);
}
