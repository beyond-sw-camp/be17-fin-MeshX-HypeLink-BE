package com.example.apiitem.item.adaptor.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemDetailRepository extends JpaRepository<ItemDetailEntity, Integer> {
    @Modifying
    @Query(value = """
INSERT INTO item_detail_entity (
  id,
  color_id,
  size_id,
  item_detail_code,
  stock,
  item_id,
  created_at,
  updated_at
)
VALUES (
  :#{#entity.id},
  :#{#entity.color.id},
  :#{#entity.size.id},
  :#{#entity.itemDetailCode},
  :#{#entity.stock},
  :#{#entity.item.id},
  NOW(),
  NOW()
)
ON DUPLICATE KEY UPDATE
  color_id = VALUES(color_id),
  size_id = VALUES(size_id),
  item_detail_code = VALUES(item_detail_code),
  stock = VALUES(stock),
  item_id = VALUES(item_id),
  updated_at = NOW()
""", nativeQuery = true)
    void upsert(@Param("entity") ItemDetailEntity entity);
}
