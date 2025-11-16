package com.example.apidirect.item.adapter.out.persistence;

import jakarta.persistence.LockModeType;
import com.example.apidirect.item.adapter.out.entity.StoreItemDetailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreItemDetailRepository extends JpaRepository<StoreItemDetailEntity, Integer> {

    Optional<StoreItemDetailEntity> findByItemDetailCode(String itemDetailCode);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT d FROM StoreItemDetailEntity d WHERE d.itemDetailCode = :itemDetailCode")
    Optional<StoreItemDetailEntity> findByItemDetailCodeWithLock(@Param("itemDetailCode") String itemDetailCode);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT d FROM StoreItemDetailEntity d WHERE d.id = :id")
    Optional<StoreItemDetailEntity> findByIdWithLock(@Param("id") Integer id);

    List<StoreItemDetailEntity> findByStockLessThanEqual(Integer stock);

    @Query("SELECT d FROM StoreItemDetailEntity d " +
           "JOIN FETCH d.item i " +
           "LEFT JOIN FETCH i.category " +
           "WHERE i.store.id = :storeId")
    Page<StoreItemDetailEntity> findAllByStoreId(@Param("storeId") Integer storeId, Pageable pageable);

    @Query("SELECT d FROM StoreItemDetailEntity d " +
           "JOIN FETCH d.item i " +
           "LEFT JOIN FETCH i.category " +
           "WHERE i.store.id = :storeId " +
           "AND (i.koName LIKE %:keyword% OR i.enName LIKE %:keyword% OR d.itemDetailCode LIKE %:keyword%)")
    Page<StoreItemDetailEntity> findByStoreIdAndKeyword(@Param("storeId") Integer storeId,
                                                         @Param("keyword") String keyword,
                                                         Pageable pageable);

    @Query("SELECT d FROM StoreItemDetailEntity d " +
           "JOIN FETCH d.item i " +
           "LEFT JOIN FETCH i.category " +
           "WHERE i.store.id = :storeId " +
           "AND i.category.category = :category")
    Page<StoreItemDetailEntity> findByStoreIdAndCategory(@Param("storeId") Integer storeId,
                                                          @Param("category") String category,
                                                          Pageable pageable);

    @Query("SELECT d FROM StoreItemDetailEntity d " +
           "JOIN FETCH d.item i " +
           "LEFT JOIN FETCH i.category " +
           "WHERE i.store.id = :storeId " +
           "AND d.stock <= :minStock")
    Page<StoreItemDetailEntity> findByStoreIdAndLowStock(@Param("storeId") Integer storeId,
                                                          @Param("minStock") Integer minStock,
                                                          Pageable pageable);

    @Query("SELECT d FROM StoreItemDetailEntity d " +
           "JOIN FETCH d.item i " +
           "LEFT JOIN FETCH i.category " +
           "WHERE d.itemDetailCode = :itemDetailCode " +
           "AND i.store.id = :storeId")
    Optional<StoreItemDetailEntity> findByItemDetailCodeAndStoreId(@Param("itemDetailCode") String itemDetailCode,
                                                                    @Param("storeId") Integer storeId);

    @Query("SELECT d FROM StoreItemDetailEntity d " +
           "JOIN FETCH d.item i " +
           "LEFT JOIN FETCH i.category " +
           "WHERE i.itemCode = :itemCode " +
           "AND d.itemDetailCode = :itemDetailCode " +
           "AND i.store.id = :storeId")
    Optional<StoreItemDetailEntity> findByItemCodeAndItemDetailCodeAndStoreId(@Param("itemCode") String itemCode,
                                                                               @Param("itemDetailCode") String itemDetailCode,
                                                                               @Param("storeId") Integer storeId);

    @Query("SELECT d.itemDetailCode FROM StoreItemDetailEntity d WHERE d.itemDetailCode IN :itemDetailCodes")
    List<String> findExistingItemDetailCodes(@Param("itemDetailCodes") List<String> itemDetailCodes);

    Page<StoreItemDetailEntity> findAll(Pageable pageable);

}
