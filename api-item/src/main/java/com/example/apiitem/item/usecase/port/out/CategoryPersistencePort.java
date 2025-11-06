package com.example.apiitem.item.usecase.port.out;

import com.example.apiitem.item.domain.Category;

public interface CategoryPersistencePort {
    Category findByName(String category);

}
