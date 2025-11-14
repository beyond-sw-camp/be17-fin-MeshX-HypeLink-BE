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
        itemRepository.upsert(itemEntity);

        StoreItemEntity savedItem = itemRepository.findByItemCode(itemEntity.getItemCode())
                .orElseThrow(() -> new RuntimeException("Item not found after upsert"));

        return ItemMapper.toDomain(savedItem);
    }

    @Override
    public Optional<StoreItem> findByItemCode(String itemCode) {
        return itemRepository.findByItemCodeWithDetails(itemCode)
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
