package org.example.apidirect.item.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.item.adapter.out.entity.StoreItemDetailEntity;
import org.example.apidirect.item.adapter.out.entity.StoreItemEntity;
import org.example.apidirect.item.adapter.out.entity.StoreItemImageEntity;
import org.example.apidirect.item.adapter.out.mapper.ItemDetailMapper;
import org.example.apidirect.item.adapter.out.mapper.ItemImageMapper;
import org.example.apidirect.item.adapter.out.mapper.ItemMapper;
import org.example.apidirect.item.domain.StoreItem;
import org.example.apidirect.item.usecase.port.out.ItemPersistencePort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class ItemPersistenceAdaptor implements ItemPersistencePort {

    private final StoreItemRepository itemRepository;
    private final StoreItemDetailRepository itemDetailRepository;
    private final StoreItemImageRepository itemImageRepository;

    @Override
    public StoreItem save(StoreItem item) {
        // 1. Item 저장
        StoreItemEntity itemEntity = ItemMapper.toEntity(item);
        StoreItemEntity savedItem = itemRepository.save(itemEntity);

        // 2. ItemDetail 저장
        if (item.getItemDetails() != null && !item.getItemDetails().isEmpty()) {
            List<StoreItemDetailEntity> detailEntities = item.getItemDetails().stream()
                    .map(detail -> ItemDetailMapper.toEntityWithIds(detail, detail.getColorId(), detail.getSizeId(), savedItem))
                    .collect(Collectors.toList());
            itemDetailRepository.saveAll(detailEntities);
        }

        // 3. ItemImage 저장
        if (item.getItemImages() != null && !item.getItemImages().isEmpty()) {
            List<StoreItemImageEntity> imageEntities = item.getItemImages().stream()
                    .map(image -> ItemImageMapper.toEntity(image, savedItem))
                    .collect(Collectors.toList());
            itemImageRepository.saveAll(imageEntities);
        }

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
