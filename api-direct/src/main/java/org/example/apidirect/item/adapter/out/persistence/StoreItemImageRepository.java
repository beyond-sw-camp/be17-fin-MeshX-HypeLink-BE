package org.example.apidirect.item.adapter.out.persistence;

import org.example.apidirect.item.adapter.out.entity.StoreItemImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreItemImageRepository extends JpaRepository<StoreItemImageEntity, Integer> {

    List<StoreItemImageEntity> findByItem_ItemCode(String itemCode);

    void deleteByItem_ItemCode(String itemCode);

    @Modifying
    @Query(value = """
INSERT INTO store_item_image (
  id,
  item_id,
  sort_index,
  original_filename,
  saved_path,
  content_type,
  file_size,
  created_at,
  updated_at
)
VALUES (
  :#{#entity.id},
  :#{#entity.item.id},
  :#{#entity.sortIndex},
  :#{#entity.originalFilename},
  :#{#entity.savedPath},
  :#{#entity.contentType},
  :#{#entity.fileSize},
  NOW(),
  NOW()
)
ON DUPLICATE KEY UPDATE
  item_id = VALUES(item_id),
  sort_index = VALUES(sort_index),
  original_filename = VALUES(original_filename),
  saved_path = VALUES(saved_path),
  content_type = VALUES(content_type),
  file_size = VALUES(file_size),
  updated_at = NOW()
""", nativeQuery = true)
    void upsert(@Param("entity") StoreItemImageEntity entity);
}
