package org.example.apidirect.item.adapter.out.persistence;

import jakarta.persistence.LockModeType;
import org.example.apidirect.item.adapter.out.entity.StoreItemDetailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreItemDetailRepository extends JpaRepository<StoreItemDetailEntity, Integer> {

    Optional<StoreItemDetailEntity> findByItemDetailCode(String itemDetailCode);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT d FROM StoreItemDetailEntity d WHERE d.itemDetailCode = :itemDetailCode")
    Optional<StoreItemDetailEntity> findByItemDetailCodeWithLock(@Param("itemDetailCode") String itemDetailCode);

    List<StoreItemDetailEntity> findByStockLessThanEqual(Integer stock);

    @Query("SELECT d FROM StoreItemDetailEntity d " +
           "JOIN FETCH d.item i " +
           "LEFT JOIN FETCH d.color " +
           "LEFT JOIN FETCH d.size")
    Page<StoreItemDetailEntity> findAllWithDetails(Pageable pageable);

    @Query("SELECT d FROM StoreItemDetailEntity d " +
           "JOIN FETCH d.item i " +
           "LEFT JOIN FETCH d.color " +
           "LEFT JOIN FETCH d.size " +
           "WHERE i.koName LIKE %:keyword% OR i.enName LIKE %:keyword% OR d.itemDetailCode LIKE %:keyword%")
    Page<StoreItemDetailEntity> findByKeyword(@Param("keyword") String keyword,
                                                         Pageable pageable);

    @Query("SELECT d FROM StoreItemDetailEntity d " +
           "JOIN FETCH d.item i " +
           "LEFT JOIN FETCH d.color " +
           "LEFT JOIN FETCH d.size " +
           "WHERE i.category = :category")
    Page<StoreItemDetailEntity> findByCategory(@Param("category") String category,
                                                          Pageable pageable);

    @Query("SELECT d FROM StoreItemDetailEntity d " +
           "JOIN FETCH d.item i " +
           "LEFT JOIN FETCH d.color " +
           "LEFT JOIN FETCH d.size " +
           "WHERE d.stock <= :minStock")
    Page<StoreItemDetailEntity> findByLowStock(@Param("minStock") Integer minStock,
                                                Pageable pageable);

    Page<StoreItemDetailEntity> findAll(Pageable pageable);
}
