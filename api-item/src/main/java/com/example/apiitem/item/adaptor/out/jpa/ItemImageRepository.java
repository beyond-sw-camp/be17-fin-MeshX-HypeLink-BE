package com.example.apiitem.item.adaptor.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImageRepository extends JpaRepository<ItemImageEntity, Integer> {
    List<ItemImageEntity> findByItem(ItemEntity item);
}
