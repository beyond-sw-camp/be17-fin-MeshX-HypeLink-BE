package org.example.apidirect.customer.adapter.out.mapper;

import org.example.apidirect.customer.adapter.out.entity.CustomerEntity;
import org.example.apidirect.customer.domain.Customer;

public class CustomerMapper {

    public static Customer toDomain(CustomerEntity entity) {
        if (entity == null) return null;

        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .phone(entity.getPhone())
                .birthDate(entity.getBirthDate())
                .build();
    }

    public static CustomerEntity toEntity(Customer domain) {
        if (domain == null) return null;

        return CustomerEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .phone(domain.getPhone())
                .birthDate(domain.getBirthDate())
                .build();
    }
}
