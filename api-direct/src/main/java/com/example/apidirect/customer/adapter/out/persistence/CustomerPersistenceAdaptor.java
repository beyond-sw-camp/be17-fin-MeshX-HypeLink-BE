package com.example.apidirect.customer.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import com.example.apidirect.customer.adapter.out.entity.CustomerEntity;
import com.example.apidirect.customer.adapter.out.mapper.CustomerMapper;
import com.example.apidirect.customer.domain.Customer;
import com.example.apidirect.customer.usecase.port.out.CustomerPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class CustomerPersistenceAdaptor implements CustomerPersistencePort {

    private final CustomerRepository customerRepository;

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = CustomerMapper.toEntity(customer);
        CustomerEntity saved = customerRepository.save(entity);
        return CustomerMapper.toDomain(saved);
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return customerRepository.findById(id)
                .map(CustomerMapper::toDomain);
    }

    @Override
    public Optional<Customer> findByPhone(String phone) {
        return customerRepository.findByPhone(phone)
                .map(CustomerMapper::toDomain);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return customerRepository.existsByPhone(phone);
    }

    @Override
    public boolean existsById(Integer id) {
        return customerRepository.existsById(id);
    }

    @Override
    public Page<Customer> findByKeyword(String keyword, Pageable pageable) {
        return customerRepository.findByKeyword(keyword, pageable)
                .map(CustomerMapper::toDomain);
    }

    @Override
    public Page<Customer> findByAgeRange(int minAge, int maxAge, Pageable pageable) {
        return customerRepository.findByAgeRange(minAge, maxAge, pageable)
                .map(CustomerMapper::toDomain);
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(CustomerMapper::toDomain);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void upsert(Integer id, String name, String phone, java.time.LocalDate birthDate,
                       java.time.LocalDateTime createdAt, java.time.LocalDateTime updatedAt) {
        customerRepository.upsert(id, name, phone, birthDate, createdAt, updatedAt);
    }
}
