package com.example.apiitem.item.usecase.port.out;

import com.example.apiitem.item.domain.Category;

import java.util.List;

public interface CategoryPersistencePort {
    Category findByName(String category);

    void saveAllWithId(List<Category> categories);
}
