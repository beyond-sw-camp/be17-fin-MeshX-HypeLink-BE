package com.example.apiitem.item.adaptor.out;

import MeshX.common.PersistenceAdapter;
import MeshX.common.exception.BaseException;
import com.example.apiitem.item.adaptor.out.jpa.CategoryEntity;
import com.example.apiitem.item.adaptor.out.jpa.CategoryRepository;
import com.example.apiitem.item.domain.Category;
import com.example.apiitem.item.usecase.port.out.CategoryPersistencePort;
import com.example.apiitem.util.CategoryMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class CategoryPersistenceAdaptor implements CategoryPersistencePort {
    private final CategoryRepository categoryRepository;

    @Override
    public Category findByName(String category) {
        CategoryEntity entity = findCategoryByCategoryName(category);
        return CategoryMapper.toDomain(entity);
    }

    private CategoryEntity findCategoryByCategoryName(String category) {
        Optional<CategoryEntity> optional = categoryRepository.findByCategory(category);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new BaseException(null);
    }
}
