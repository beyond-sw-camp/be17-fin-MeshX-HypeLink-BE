package com.example.apiitem.item.adaptor.out.persistence;

import MeshX.common.PersistenceAdapter;
import MeshX.common.exception.BaseException;
import com.example.apiitem.item.adaptor.out.jpa.*;
import com.example.apiitem.item.domain.Item;
import com.example.apiitem.item.domain.ItemImage;
import com.example.apiitem.item.usecase.port.in.request.CreateItemImageCommand;
import com.example.apiitem.item.usecase.port.out.ItemImagePersistencePort;
import com.example.apiitem.util.ItemImageMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class ItemImagePersistenceAdapter implements ItemImagePersistencePort {
    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;

    @Override
    public void save(ItemImage itemImage, Integer itemId) {
        ItemEntity item = findItemEntityById(itemId);
        ItemImageEntity image = ItemImageMapper.toEntity(itemImage, item);

        itemImageRepository.save(image);
    }

    @Override
    public void updateImageIndex(CreateItemImageCommand command) {
        ItemImageEntity entity = findItemImageById(command);
        entity.updateIndex(command.getIndex());

        if(!Objects.equals(entity.getSortIndex(), command.getIndex())) {
            entity.updateIndex(command.getIndex());
            itemImageRepository.save(entity);
        }
    }

    private ItemImageEntity findItemImageById(CreateItemImageCommand command) {
        Optional<ItemImageEntity> optional = itemImageRepository.findById(command.getId());
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new BaseException(null);
    }

    @Override
    public void delete(ItemImage itemImage) {
        itemImageRepository.deleteById(itemImage.getId());
    }

    @Override
    public List<ItemImage> findByItem(Item item) {
        ItemEntity itemEntity = findItemEntityById(item.getId());

        List<ItemImageEntity> imageEntities = itemImageRepository.findByItem(itemEntity);

        return imageEntities.stream().map(ItemImageMapper::toDomain)
                .toList();
    }

    @Override
    public void saveAllWithId(List<ItemImage> itemImages, Item item) {
        ItemEntity itemEntity = findItemEntityById(item.getId());
        List<ItemImageEntity> itemImageEntities = itemImages.stream()
                .map(one -> ItemImageMapper.toEntity(one, itemEntity))
                .toList();
        itemImageEntities.forEach(itemImageRepository::upsert);
    }

    @Override
    public void deleteAllWithItemId(Integer itemId) {
        List<ItemImageEntity> detailEntities = itemImageRepository.findByItem_Id(itemId);
        itemImageRepository.deleteAll(detailEntities);
    }

    private ItemEntity findItemEntityById(Integer id) {
        Optional<ItemEntity> item = itemRepository.findById(id);
        if(item.isPresent()) {
            return item.get();
        }
        throw new BaseException(null);
    }
}
