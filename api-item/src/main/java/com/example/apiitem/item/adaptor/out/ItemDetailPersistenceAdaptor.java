package com.example.apiitem.item.adaptor.out;

import MeshX.common.PersistenceAdapter;
import MeshX.common.exception.BaseException;
import com.example.apiitem.item.adaptor.out.jpa.*;
import com.example.apiitem.item.domain.Item;
import com.example.apiitem.item.domain.ItemDetail;
import com.example.apiitem.item.usecase.port.out.ItemDetailPersistencePort;
import com.example.apiitem.util.ItemDetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class ItemDetailPersistenceAdaptor implements ItemDetailPersistencePort {
    private final ItemDetailRepository itemDetailRepository;
    private final ItemRepository itemRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;

    @Override
    public void saveAll(List<ItemDetail> itemDetails, Item entity) {
        ItemEntity itemEntity = findItemEntityById(entity);
        List<ItemDetailEntity> entities = itemDetails.stream().map(one -> {
            SizeEntity size = findSizeEntityBySize(one);
            ColorEntity color = findColorEntityByName(one);
            return ItemDetailMapper.toEntity(one, color, size, itemEntity);
        }).toList();

        itemDetailRepository.saveAll(entities);
    }

    @Override
    public void saveAllWithId(List<ItemDetail> itemDetails, Item entity) {
        ItemEntity itemEntity = findItemEntityById(entity);
        List<ItemDetailEntity> entities = itemDetails.stream().map(one -> {
            SizeEntity size = findSizeEntity(one);
            ColorEntity color = findColorEntity(one);
            return ItemDetailMapper.toEntity(one, color, size, itemEntity);
        }).toList();

        entities.forEach(itemDetailRepository::upsert);
    }

    @Override
    public ItemDetail findById(Integer id) {
        ItemDetailEntity itemDetailEntity = findItemDetailEntityById(id);

        return ItemDetailMapper.toDomain(itemDetailEntity);
    }

    @Override
    public ItemDetail findByItemDetailCode(String detailCode) {
        ItemDetailEntity itemDetailEntity = findItemDetailEntityByItemDetailCode(detailCode);

        return ItemDetailMapper.toDomain(itemDetailEntity);
    }

    @Override
    public List<ItemDetail> findByItemId(Integer id) {
        List<ItemDetailEntity> itemDetails = itemDetailRepository.findByItem_Id(id);
        return itemDetails.stream()
                .map(ItemDetailMapper::toSimpleDomain)
                .toList();
    }

    private ItemDetailEntity findItemDetailEntityById(Integer id) {
        Optional<ItemDetailEntity> optional = itemDetailRepository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new BaseException(null);
    }

    private ItemDetailEntity findItemDetailEntityByItemDetailCode(String itemDetailCode) {
        Optional<ItemDetailEntity> optional = itemDetailRepository.findByItemDetailCode(itemDetailCode);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new BaseException(null);
    }

    private SizeEntity findSizeEntity(ItemDetail domain) {
        Optional<SizeEntity> optional = sizeRepository.findById(domain.getSizeId());
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new BaseException(null);
    }

    private SizeEntity findSizeEntityBySize(ItemDetail domain) {
        Optional<SizeEntity> optional = sizeRepository.findBySize(domain.getSize());
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new BaseException(null);
    }

    private ColorEntity findColorEntity(ItemDetail domain) {
        Optional<ColorEntity> optional = colorRepository.findById(domain.getColorId());
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new BaseException(null);
    }

    private ColorEntity findColorEntityByName(ItemDetail domain) {
        Optional<ColorEntity> optional = colorRepository.findByColorName(domain.getColorName());
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new BaseException(null);
    }

    private ItemEntity findItemEntityById(Item entity) {
        Optional<ItemEntity> optional = itemRepository.findById(entity.getId());
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new BaseException(null);
    }
}
