package com.example.apidirect.item.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import com.example.apidirect.item.adapter.out.entity.StoreItemDetailEntity;
import com.example.apidirect.item.adapter.out.entity.StoreItemEntity;
import com.example.apidirect.item.adapter.out.mapper.ItemDetailMapper;
import com.example.apidirect.item.domain.StoreItemDetail;
import com.example.apidirect.item.usecase.port.out.ItemDetailPersistencePort;

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
        // 재고 업데이트 전용 - ID 필수 (재고 차감 시 사용)
        if (detail.getId() == null) {
            throw new RuntimeException("재고 업데이트는 기존 ItemDetail ID가 필요합니다");
        }

        // 기존 엔티티 조회
        StoreItemDetailEntity existingEntity = itemDetailRepository.findById(detail.getId())
                .orElseThrow(() -> new RuntimeException("ItemDetail not found - id: " + detail.getId()));

        // 재고 차이 계산 및 업데이트
        // 예: 도메인 stock=95, 엔티티 stock=100 → updateStock(-5) → 95
        Integer stockDifference = detail.getStock() - existingEntity.getStock();
        existingEntity.updateStock(stockDifference);

        StoreItemDetailEntity saved = itemDetailRepository.save(existingEntity);
        return ItemDetailMapper.toDomain(saved);
    }

    @Override
    public List<StoreItemDetail> saveAll(List<StoreItemDetail> details) {
        return details.stream()
                .map(this::save)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StoreItemDetail> findById(Integer id) {
        return itemDetailRepository.findById(id)
                .map(ItemDetailMapper::toDomain);
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
