package com.example.apidirect.item.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import com.example.apidirect.auth.adapter.out.entity.StoreEntity;
import com.example.apidirect.auth.adapter.out.persistence.StoreRepository;
import com.example.apidirect.item.adapter.out.entity.StoreCategoryEntity;
import com.example.apidirect.item.adapter.out.mapper.CategoryMapper;
import com.example.apidirect.item.domain.Category;
import com.example.apidirect.item.usecase.port.out.CategoryPersistencePort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class CategoryPersistenceAdaptor implements CategoryPersistencePort {

    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;

    @Override
    public Category save(String category, Integer storeId) {
        StoreEntity store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found: " + storeId));

        StoreCategoryEntity entity = CategoryMapper.toEntity(category, store);
        StoreCategoryEntity saved = categoryRepository.save(entity);
        return CategoryMapper.toDomain(saved);
    }

    @Override
    public List<Category> saveAll(List<String> categories, Integer storeId) {
        StoreEntity store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found: " + storeId));

        List<StoreCategoryEntity> entities = categories.stream()
                .map(cat -> CategoryMapper.toEntity(cat, store))
                .collect(Collectors.toList());

        List<StoreCategoryEntity> saved = categoryRepository.saveAll(entities);
        return saved.stream()
                .map(CategoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveAllWithId(List<Category> categories, Integer storeId) {
        StoreEntity store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found: " + storeId));

        List<StoreCategoryEntity> entities = categories.stream()
                .map(cat -> CategoryMapper.toEntity(cat, store))
                .collect(Collectors.toList());

        entities.forEach(categoryRepository::upsert);
    }

    @Override
    public Optional<Category> findByName(String category, Integer storeId) {
        return categoryRepository.findByCategoryAndStoreId(category, storeId)
                .map(CategoryMapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean exists(String category, Integer storeId) {
        return categoryRepository.existsByCategoryAndStoreId(category, storeId);
    }
}
