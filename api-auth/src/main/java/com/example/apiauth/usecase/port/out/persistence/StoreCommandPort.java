package com.example.apiauth.usecase.port.out.persistence;

import com.example.apiauth.domain.model.Store;

public interface StoreCommandPort {

    Store save(Store store);

    void delete(Integer id);
}
