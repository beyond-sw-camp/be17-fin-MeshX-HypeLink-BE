package org.example.apidirect.item.adapter.out.persistence;

import org.example.apidirect.item.adapter.out.entity.StoreItemDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreItemDetailRepository extends JpaRepository<StoreItemDetailEntity, Integer> {

    Optional<StoreItemDetailEntity> findByItemDetailCode(String itemDetailCode);

    List<StoreItemDetailEntity> findByStockLessThanEqual(Integer stock);
}
