package com.example.apiitem.item.adaptor.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemImageRepository extends JpaRepository<ItemImageEntity, Integer> {
    List<ItemImageEntity> findByItem(ItemEntity item);
    List<ItemImageEntity> findByItem_Id(Integer itemId);

    @Modifying
    @Query(value = """
INSERT INTO item_image_entity (
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
    void upsert(@Param("entity") ItemImageEntity entity);

}
