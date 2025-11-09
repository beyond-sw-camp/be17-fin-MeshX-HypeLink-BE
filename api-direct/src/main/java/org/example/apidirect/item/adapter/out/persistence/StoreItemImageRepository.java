package org.example.apidirect.item.adapter.out.persistence;

import org.example.apidirect.item.adapter.out.entity.StoreItemImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreItemImageRepository extends JpaRepository<StoreItemImageEntity, Integer> {

    List<StoreItemImageEntity> findByItem_ItemCode(String itemCode);

    void deleteByItem_ItemCode(String itemCode);
}
