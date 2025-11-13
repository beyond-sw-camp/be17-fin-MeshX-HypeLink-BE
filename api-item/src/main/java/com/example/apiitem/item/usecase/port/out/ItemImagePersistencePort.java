package com.example.apiitem.item.usecase.port.out;

import com.example.apiitem.item.domain.Item;
import com.example.apiitem.item.domain.ItemImage;
import com.example.apiitem.item.usecase.port.in.request.CreateItemImageCommand;

import java.util.List;

public interface ItemImagePersistencePort {
    void save(ItemImage entity, Integer itemId);

    void updateImageIndex(CreateItemImageCommand one);

    void delete(ItemImage itemImage);

    List<ItemImage> findByItem(Item item);

    void saveAllWithId(List<ItemImage> itemImages, Item item);

    void deleteAllWithItemId(Integer itemId);
}
