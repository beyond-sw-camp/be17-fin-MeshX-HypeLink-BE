package com.example.apidirect.item.adapter.out.persistence;

import com.example.apidirect.item.adapter.out.entity.StoreItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreItemRepository extends JpaRepository<StoreItemEntity, Integer> {

    Optional<StoreItemEntity> findByItemCode(String itemCode);

    @Query("SELECT i FROM StoreItemEntity i WHERE i.itemCode = :itemCode AND i.store.id = :storeId")
    Optional<StoreItemEntity> findByItemCodeAndStoreId(@Param("itemCode") String itemCode, @Param("storeId") Integer storeId);

    boolean existsByItemCode(String itemCode);

    @Query("SELECT i FROM StoreItemEntity i " +
           "WHERE i.itemCode = :itemCode")
    Optional<StoreItemEntity> findByItemCodeWithDetails(@Param("itemCode") String itemCode);

    @Query("SELECT i FROM StoreItemEntity i " +
           "WHERE i.itemCode = :itemCode AND i.store.id = :storeId")
    Optional<StoreItemEntity> findByItemCodeAndStoreIdWithDetails(@Param("itemCode") String itemCode, @Param("storeId") Integer storeId);

    @Modifying
    @Query(value = """
INSERT INTO store_item (
  id,
  item_code,
  store_id,
  unit_price,
  amount,
  en_name,
  ko_name,
  content,
  company,
  category,
  created_at,
  updated_at
)
VALUES (
  :#{#entity.id},
  :#{#entity.itemCode},
  :#{#entity.storeId},
  :#{#entity.unitPrice},
  :#{#entity.amount},
  :#{#entity.enName},
  :#{#entity.koName},
  :#{#entity.content},
  :#{#entity.company},
  :#{#entity.category},
  NOW(),
  NOW()
)
ON DUPLICATE KEY UPDATE
  item_code = VALUES(item_code),
  store_id = VALUES(store_id),
  unit_price = VALUES(unit_price),
  amount = VALUES(amount),
  en_name = VALUES(en_name),
  ko_name = VALUES(ko_name),
  content = VALUES(content),
  company = VALUES(company),
  category = VALUES(category),
  updated_at = NOW()
""", nativeQuery = true)
    void upsert(@Param("entity") StoreItemEntity entity);

}
