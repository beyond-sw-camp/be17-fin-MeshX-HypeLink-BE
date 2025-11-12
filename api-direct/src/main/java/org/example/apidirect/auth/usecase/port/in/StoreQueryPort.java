package org.example.apidirect.auth.usecase.port.in;

import org.example.apidirect.auth.domain.Store;

public interface StoreQueryPort {
    Store findById(Integer id);
}
