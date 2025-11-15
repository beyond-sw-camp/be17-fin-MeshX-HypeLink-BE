package com.example.apidirect.item.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    @Transactional
    public StoreItem save(StoreItem item) {
        StoreItemEntity itemEntity = ItemMapper.toEntity(item);

        // 기존 아이템 확인
        Optional<StoreItemEntity> existing = itemRepository.findByItemCodeAndStoreId(
                itemEntity.getItemCode(), itemEntity.getStoreId());

        if (existing.isPresent()) {
            // 기존 아이템 업데이트
            StoreItemEntity existingEntity = existing.get();
            existingEntity.updateUnitPrice(itemEntity.getUnitPrice());
            existingEntity.updateAmount(itemEntity.getAmount());
            existingEntity.updateEnName(itemEntity.getEnName());
            existingEntity.updateKoName(itemEntity.getKoName());
            existingEntity.updateContent(itemEntity.getContent());
            existingEntity.updateCompany(itemEntity.getCompany());
            existingEntity.updateCategory(itemEntity.getCategory());
            StoreItemEntity updated = itemRepository.save(existingEntity);
            return ItemMapper.toDomain(updated);
        } else {
            // 신규 저장
            StoreItemEntity saved = itemRepository.save(itemEntity);
            return ItemMapper.toDomain(saved);
        }
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
