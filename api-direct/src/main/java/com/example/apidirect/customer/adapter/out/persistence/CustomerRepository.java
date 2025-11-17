package com.example.apidirect.customer.adapter.out.persistence;

import com.example.apidirect.customer.adapter.out.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

    Optional<CustomerEntity> findByPhone(String phone);

    boolean existsByPhone(String phone);

    @Query("SELECT c FROM CustomerEntity c WHERE c.name LIKE %:keyword% OR c.phone LIKE %:keyword%")
    Page<CustomerEntity> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT c FROM CustomerEntity c WHERE " +
           "YEAR(CURRENT_DATE) - YEAR(c.birthDate) >= :minAge AND " +
           "YEAR(CURRENT_DATE) - YEAR(c.birthDate) < :maxAge")
    Page<CustomerEntity> findByAgeRange(@Param("minAge") int minAge,
                                         @Param("maxAge") int maxAge,
                                         Pageable pageable);

    @Modifying
    @Query(value = "INSERT INTO customer (id, name, phone, birth_date, created_at, updated_at) " +
                   "VALUES (:id, :name, :phone, :birthDate, :createdAt, :updatedAt) " +
                   "ON DUPLICATE KEY UPDATE " +
                   "name = VALUES(name), " +
                   "phone = VALUES(phone), " +
                   "birth_date = VALUES(birth_date), " +
                   "updated_at = VALUES(updated_at)",
           nativeQuery = true)
    void upsert(@Param("id") Integer id,
                @Param("name") String name,
                @Param("phone") String phone,
                @Param("birthDate") LocalDate birthDate,
                @Param("createdAt") LocalDateTime createdAt,
                @Param("updatedAt") LocalDateTime updatedAt);
}
