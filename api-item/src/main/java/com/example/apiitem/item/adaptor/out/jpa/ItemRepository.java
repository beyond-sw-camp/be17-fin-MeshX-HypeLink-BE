package com.example.apiitem.item.adaptor.out.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {
    @Query("""
        select i
        from ItemEntity i
        left join fetch i.category
        left join fetch i.itemDetails
        where i.id = :id
    """)
    Optional<ItemEntity> findById(Integer id);

    @Query("""
        select i
        from ItemEntity i
        left join fetch i.category
        left join fetch i.itemDetails
        where i.itemCode = :itemCode
    """)
    Optional<ItemEntity> findByItemCode(String itemCode);

    @Query("""
        SELECT DISTINCT i
        FROM ItemEntity i
        LEFT JOIN FETCH i.itemImages ii
        """)
    List<ItemEntity> findAllWithImage();

    @EntityGraph(attributePaths = {
            "category",
            "itemDetails.color",
            "itemDetails.size"
    })
    @Query("SELECT DISTINCT i FROM ItemEntity i")
    Page<ItemEntity> findAllWithImages(Pageable pageable);

    @Modifying
    @Query(value = """
INSERT INTO item_entity (
  id,
  category_id,
  item_code,
  unit_price,
  amount,
  en_name,
  ko_name,
  content,
  company,
  created_at,
  updated_at
)
VALUES (
  :#{#entity.id},
  :#{#entity.category.id},
  :#{#entity.itemCode},
  :#{#entity.unitPrice},
  :#{#entity.amount},
  :#{#entity.enName},
  :#{#entity.koName},
  :#{#entity.content},
  :#{#entity.company},
  NOW(),
  NOW()
)
ON DUPLICATE KEY UPDATE
  category_id = VALUES(category_id),
  item_code = VALUES(item_code),
  unit_price = VALUES(unit_price),
  amount = VALUES(amount),
  en_name = VALUES(en_name),
  ko_name = VALUES(ko_name),
  content = VALUES(content),
  company = VALUES(company),
  updated_at = NOW()
""", nativeQuery = true)
    void upsert(@Param("entity") ItemEntity entity);

}
