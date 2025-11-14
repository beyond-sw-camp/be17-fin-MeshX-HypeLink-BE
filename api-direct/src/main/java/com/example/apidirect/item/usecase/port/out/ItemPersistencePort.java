package com.example.apidirect.item.usecase.port.out;

import com.example.apidirect.item.domain.StoreItem;

import java.util.List;
import java.util.Optional;

public interface ItemPersistencePort {

    StoreItem save(StoreItem item);

    Optional<StoreItem> findByItemCode(String itemCode);

    List<StoreItem> findAll();

    boolean exists(String itemCode);

    void delete(String itemCode);
}
