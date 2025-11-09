package org.example.apidirect.item.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.item.adapter.out.entity.StoreItemDetailEntity;
import org.example.apidirect.item.adapter.out.mapper.ItemDetailMapper;
import org.example.apidirect.item.domain.StoreItemDetail;
import org.example.apidirect.item.usecase.port.out.ItemDetailPersistencePort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class ItemDetailPersistenceAdaptor implements ItemDetailPersistencePort {

    private final StoreItemDetailRepository itemDetailRepository;
    private final StoreItemRepository itemRepository;

    @Override
    public StoreItemDetail save(StoreItemDetail detail) {
        var item = itemRepository.findByItemCode(detail.getItemDetailCode())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        StoreItemDetailEntity entity = ItemDetailMapper.toEntity(detail, item);
        StoreItemDetailEntity saved = itemDetailRepository.save(entity);
        return ItemDetailMapper.toDomain(saved);
    }

    @Override
    public List<StoreItemDetail> saveAll(List<StoreItemDetail> details) {
        return details.stream()
                .map(this::save)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StoreItemDetail> findByItemDetailCode(String itemDetailCode) {
        return itemDetailRepository.findByItemDetailCode(itemDetailCode)
                .map(ItemDetailMapper::toDomain);
    }

    @Override
    public List<StoreItemDetail> findLowStock(Integer threshold) {
        return itemDetailRepository.findByStockLessThanEqual(threshold).stream()
                .map(ItemDetailMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void updateStock(String itemDetailCode, Integer stockChange) {
        itemDetailRepository.findByItemDetailCode(itemDetailCode)
                .ifPresent(entity -> {
                    entity.updateStock(stockChange);
                    itemDetailRepository.save(entity);
                });
    }
}
