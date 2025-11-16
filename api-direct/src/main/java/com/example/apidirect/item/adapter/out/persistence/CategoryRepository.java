package com.example.apidirect.item.adapter.out.persistence;

import com.example.apidirect.item.adapter.out.entity.StoreCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<StoreCategoryEntity, Integer> {

    @Query("SELECT c FROM StoreCategoryEntity c WHERE c.category = :category AND c.store.id = :storeId")
    Optional<StoreCategoryEntity> findByCategoryAndStoreId(@Param("category") String category, @Param("storeId") Integer storeId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM StoreCategoryEntity c WHERE c.category = :category AND c.store.id = :storeId")
    boolean existsByCategoryAndStoreId(@Param("category") String category, @Param("storeId") Integer storeId);

    @Modifying
    @Query(value = """
INSERT INTO store_category (id, category, store_id, created_at, updated_at)
VALUES (
  :#{#entity.id},
  :#{#entity.category},
  :#{#entity.store.id},
  NOW(),
  NOW()
)
ON DUPLICATE KEY UPDATE
  category = VALUES(category),
  store_id = VALUES(store_id),
  updated_at = NOW()
""", nativeQuery = true)
    void upsert(@Param("entity") StoreCategoryEntity entity);
}
