package org.example.apidirect.item.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apidirect.item.adapter.out.entity.ColorEntity;
import org.example.apidirect.item.adapter.out.entity.SizeEntity;
import org.example.apidirect.item.adapter.out.entity.StoreItemDetailEntity;
import org.example.apidirect.item.adapter.out.entity.StoreItemEntity;
import org.example.apidirect.item.adapter.out.entity.StoreItemImageEntity;
import org.example.apidirect.item.adapter.out.mapper.ItemDetailMapper;
import org.example.apidirect.item.adapter.out.mapper.ItemImageMapper;
import org.example.apidirect.item.adapter.out.mapper.ItemMapper;
import org.example.apidirect.item.domain.StoreItem;
import org.example.apidirect.item.usecase.port.out.ItemPersistencePort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public class ItemPersistenceAdaptor implements ItemPersistencePort {

    private final StoreItemRepository itemRepository;
    private final StoreItemDetailRepository itemDetailRepository;
    private final StoreItemImageRepository itemImageRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;

    @Override
    @Transactional
    public StoreItem save(StoreItem item) {
        // 1. Item 저장 (upsert 사용)
        StoreItemEntity itemEntity = ItemMapper.toEntity(item);
        itemRepository.upsert(itemEntity);

        // upsert 후 다시 조회해서 savedItem 얻기
        StoreItemEntity savedItem = itemRepository.findByItemCode(itemEntity.getItemCode())
                .orElseThrow(() -> new RuntimeException("Item not found after upsert"));

        // 2. ItemDetail 저장 (upsert 사용) - Color/Size FK 검증 포함
        if (item.getItemDetails() != null && !item.getItemDetails().isEmpty()) {
            List<StoreItemDetailEntity> detailEntities = item.getItemDetails().stream()
                    .map(detail -> {
                        // Color/Size 존재 확인
                        ColorEntity color = findColorEntity(detail.getColorId());
                        SizeEntity size = findSizeEntity(detail.getSizeId());

                        if (color == null || size == null) {
                            log.warn("[KAFKA] ItemDetail 저장 스킵 - Color({})/Size({}) 미존재: itemDetailCode={}",
                                    detail.getColorId(), detail.getSizeId(), detail.getItemDetailCode());
                            return null;
                        }

                        return ItemDetailMapper.toEntityWithIds(detail, detail.getColorId(), detail.getSizeId(), savedItem);
                    })
                    .filter(entity -> entity != null)
                    .collect(Collectors.toList());

            detailEntities.forEach(itemDetailRepository::upsert);
        }

        // 3. ItemImage 저장 (upsert 사용)
        if (item.getItemImages() != null && !item.getItemImages().isEmpty()) {
            List<StoreItemImageEntity> imageEntities = item.getItemImages().stream()
                    .map(image -> ItemImageMapper.toEntity(image, savedItem))
                    .collect(Collectors.toList());
            imageEntities.forEach(itemImageRepository::upsert);
        }

        return ItemMapper.toDomain(savedItem);
    }

    private ColorEntity findColorEntity(Integer colorId) {
        if (colorId == null) {
            return null;
        }
        Optional<ColorEntity> optional = colorRepository.findById(colorId);
        return optional.orElse(null);
    }

    private SizeEntity findSizeEntity(Integer sizeId) {
        if (sizeId == null) {
            return null;
        }
        Optional<SizeEntity> optional = sizeRepository.findById(sizeId);
        return optional.orElse(null);
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
