package org.example.apidirect.customer.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.customer.adapter.out.entity.CustomerEntity;
import org.example.apidirect.customer.adapter.out.mapper.CustomerMapper;
import org.example.apidirect.customer.domain.Customer;
import org.example.apidirect.customer.usecase.port.out.CustomerPersistencePort;
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
}
