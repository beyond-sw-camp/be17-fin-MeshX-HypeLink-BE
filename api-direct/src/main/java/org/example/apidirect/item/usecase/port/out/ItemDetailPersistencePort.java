package org.example.apidirect.item.usecase.port.out;

import org.example.apidirect.item.domain.StoreItemDetail;

import java.util.List;
import java.util.Optional;

public interface ItemDetailPersistencePort {

    StoreItemDetail save(StoreItemDetail detail);

    List<StoreItemDetail> saveAll(List<StoreItemDetail> details);

    Optional<StoreItemDetail> findByItemDetailCode(String itemDetailCode);

    List<StoreItemDetail> findLowStock(Integer threshold);

    void updateStock(String itemDetailCode, Integer stockChange);
}
