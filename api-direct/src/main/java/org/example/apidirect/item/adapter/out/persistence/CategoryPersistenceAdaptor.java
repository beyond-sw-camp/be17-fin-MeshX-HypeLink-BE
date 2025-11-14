package org.example.apidirect.item.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.item.adapter.out.entity.CategoryEntity;
import org.example.apidirect.item.adapter.out.mapper.CategoryMapper;
import org.example.apidirect.item.domain.Category;
import org.example.apidirect.item.usecase.port.out.CategoryPersistencePort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class CategoryPersistenceAdaptor implements CategoryPersistencePort {

    private final CategoryRepository categoryRepository;

    @Override
    public Category save(String category) {
        CategoryEntity entity = CategoryMapper.toEntity(category);
        CategoryEntity saved = categoryRepository.save(entity);
        return CategoryMapper.toDomain(saved);
    }

    @Override
    public List<Category> saveAll(List<String> categories) {
        List<CategoryEntity> entities = categories.stream()
                .map(CategoryMapper::toEntity)
                .collect(Collectors.toList());

        List<CategoryEntity> saved = categoryRepository.saveAll(entities);
        return saved.stream()
                .map(CategoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveAllWithId(List<Category> categories) {
        List<CategoryEntity> entities = categories.stream()
                .map(CategoryMapper::toEntity)
                .collect(Collectors.toList());

        entities.forEach(categoryRepository::upsert);
    }

    @Override
    public Optional<Category> findByName(String category) {
        return categoryRepository.findByCategory(category)
                .map(CategoryMapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean exists(String category) {
        return categoryRepository.existsByCategory(category);
    }
}
