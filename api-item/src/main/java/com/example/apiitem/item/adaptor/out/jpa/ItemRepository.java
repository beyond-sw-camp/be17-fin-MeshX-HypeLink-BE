package com.example.apiitem.item.adaptor.out.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {
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
}
