package com.example.apidirect.item.usecase.port.out;

import com.example.apidirect.item.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryPersistencePort {

    Category save(String category, Integer storeId);

    List<Category> saveAll(List<String> categories, Integer storeId);

    void saveAllWithId(List<Category> categories, Integer storeId);

    Optional<Category> findByName(String category, Integer storeId);

    List<Category> findAll();

    boolean exists(String category, Integer storeId);
}
