package com.example.apidirect.customer.usecase.port.out.mapper;

import com.example.apidirect.customer.domain.Customer;
import com.example.apidirect.customer.usecase.port.out.response.CustomerResponse;

public class CustomerResponseMapper {

    public static CustomerResponse toResponse(Customer customer) {
        if (customer == null) return null;

        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .birthDate(customer.getBirthDate())
                .build();
    }

    public static Customer toDomain(CustomerResponse response) {
        if (response == null) return null;

        return Customer.builder()
                .id(response.getId())
                .name(response.getName())
                .phone(response.getPhone())
                .birthDate(response.getBirthDate())
                .build();
    }
}
