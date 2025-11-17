package com.example.apidirect.customer.usecase.port.out;

import com.example.apidirect.customer.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerPersistencePort {
    Customer save(Customer customer);
    Optional<Customer> findById(Integer id);
    Optional<Customer> findByPhone(String phone);
    boolean existsByPhone(String phone);
    boolean existsById(Integer id);
    Page<Customer> findByKeyword(String keyword, Pageable pageable);
    Page<Customer> findByAgeRange(int minAge, int maxAge, Pageable pageable);
    Page<Customer> findAll(Pageable pageable);
    void upsert(Integer id, String name, String phone, java.time.LocalDate birthDate,
                java.time.LocalDateTime createdAt, java.time.LocalDateTime updatedAt);
}
