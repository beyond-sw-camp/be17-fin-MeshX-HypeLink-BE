package com.example.apidirect.item.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.apidirect.auth.adapter.out.entity.StoreEntity;
import com.example.apidirect.auth.adapter.out.persistence.StoreRepository;
import com.example.apidirect.item.adapter.out.entity.StoreCategoryEntity;
import com.example.apidirect.item.adapter.out.entity.StoreItemEntity;
import com.example.apidirect.item.adapter.out.mapper.ItemMapper;
import com.example.apidirect.item.domain.StoreItem;
import com.example.apidirect.item.usecase.port.out.ItemPersistencePort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public class ItemPersistenceAdaptor implements ItemPersistencePort {

    private final StoreItemRepository itemRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public StoreItem save(StoreItem item) {
        // ✅ StoreEntity 조회
        StoreEntity store = storeRepository.findById(item.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found: " + item.getStoreId()));

        // ✅ CategoryEntity 조회 또는 생성
        StoreCategoryEntity category = categoryRepository.findByCategoryAndStoreId(item.getCategory(), item.getStoreId())
                .orElseGet(() -> {
                    StoreCategoryEntity newCategory = StoreCategoryEntity.builder()
                            .category(item.getCategory())
                            .store(store)
                            .build();
                    return categoryRepository.save(newCategory);
                });

        StoreItemEntity itemEntity = ItemMapper.toEntity(item, store, category);

        // ✅ upsert 실행 (모놀리식과 동일한 id 유지)
        itemRepository.upsert(itemEntity);

        // upsert 후 저장된 엔티티 조회
        StoreItemEntity saved = itemRepository.findByItemCodeAndStoreId(
                itemEntity.getItemCode(), itemEntity.getStore().getId())
                .orElseThrow(() -> new RuntimeException("Upsert 후 조회 실패: " + item.getItemCode()));

        return ItemMapper.toDomain(saved);
    }

    @Override
    public Optional<StoreItem> findByItemCode(String itemCode) {
        return itemRepository.findByItemCodeWithDetails(itemCode)
                .map(ItemMapper::toDomain);
    }

    @Override
    public Optional<StoreItem> findByItemCodeAndStoreId(String itemCode, Integer storeId) {
        return itemRepository.findByItemCodeAndStoreIdWithDetails(itemCode, storeId)
                .map(ItemMapper::toDomain);
    }

    @Override
    public List<StoreItem> findAll() {
        return itemRepository.findAll().stream()
                .map(ItemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean exists(String itemCode) {
        return itemRepository.existsByItemCode(itemCode);
    }

    @Override
    public void delete(String itemCode) {
        itemRepository.findByItemCode(itemCode)
                .ifPresent(itemRepository::delete);
    }
}
