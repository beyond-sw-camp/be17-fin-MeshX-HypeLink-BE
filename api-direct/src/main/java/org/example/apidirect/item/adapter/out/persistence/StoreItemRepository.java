package org.example.apidirect.item.adapter.out.persistence;

import org.example.apidirect.item.adapter.out.entity.StoreItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreItemRepository extends JpaRepository<StoreItemEntity, Integer> {

    Optional<StoreItemEntity> findByItemCode(String itemCode);

    boolean existsByItemCode(String itemCode);

    @Query("SELECT i FROM StoreItemEntity i " +
           "LEFT JOIN FETCH i.itemDetails " +
           "LEFT JOIN FETCH i.itemImages " +
           "WHERE i.itemCode = :itemCode")
    Optional<StoreItemEntity> findByItemCodeWithDetails(@Param("itemCode") String itemCode);
}
