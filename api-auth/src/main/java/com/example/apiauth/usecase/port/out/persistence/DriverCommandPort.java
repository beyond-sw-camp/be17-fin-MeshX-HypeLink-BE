package com.example.apiauth.usecase.port.out.persistence;

import com.example.apiauth.domain.model.Driver;

public interface DriverCommandPort {

    Driver save(Driver driver);

    void delete(Integer id);
}
