package org.example.apidirect.item.usecase.port.out;

import org.example.apidirect.item.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryPersistencePort {

    Category save(String category);

    List<Category> saveAll(List<String> categories);

    void saveAllWithId(List<Category> categories);

    Optional<Category> findByName(String category);

    List<Category> findAll();

    boolean exists(String category);
}
