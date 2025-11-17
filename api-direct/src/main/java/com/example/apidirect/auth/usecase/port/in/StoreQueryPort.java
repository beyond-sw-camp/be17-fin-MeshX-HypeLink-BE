package com.example.apidirect.auth.usecase.port.in;

import com.example.apidirect.auth.domain.Store;

public interface StoreQueryPort {
    Store findById(Integer id);
}
