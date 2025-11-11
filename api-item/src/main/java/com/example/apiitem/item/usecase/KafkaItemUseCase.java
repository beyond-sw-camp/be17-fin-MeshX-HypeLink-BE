package com.example.apiitem.item.usecase;

import MeshX.common.UseCase;
import com.example.apiitem.item.domain.*;
import com.example.apiitem.item.usecase.port.in.KafkaItemInPort;
import com.example.apiitem.item.usecase.port.in.request.kafka.*;
import com.example.apiitem.item.usecase.port.out.*;
import com.example.apiitem.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional
@RequiredArgsConstructor
public class KafkaItemUseCase implements KafkaItemInPort {
    private final ItemPersistencePort itemPersistencePort;
    private final ItemDetailPersistencePort itemDetailPersistencePort;
    private final ItemImagePersistencePort itemImagePersistencePort;
    private final SizePersistencePort sizePersistencePort;
    private final CategoryPersistencePort categoryPersistencePort;
    private final ColorPersistencePort colorPersistencePort;

    @Override
    public void save(KafkaItemCommand command) {
        Item domain = ItemMapper.toDomain(command);
        Item item = itemPersistencePort.saveWithCategoryId(domain);

        List<ItemDetail> itemDetails = command.getItemDetails().stream()
                .map(ItemDetailMapper::toDomain)
                .toList();
        itemDetailPersistencePort.saveAllWithId(itemDetails, item);

        List<ItemImage> itemImages = command.getItemImages().stream()
                .map(ItemImageMapper::toDomain)
                .toList();
        itemImagePersistencePort.saveAllWithId(itemImages, item);
    }

    @Override
    public void saveSizes(KafkaSizeList command) {
        List<Size> sizes = command.getSizes().stream().map(SizeMapper::toDomain).toList();
        sizePersistencePort.saveAllWithId(sizes);
    }

    @Override
    public void saveCategories(KafkaCategoryList command) {
        List<Category> categories = command.getCategories().stream().map(CategoryMapper::toDomain).toList();
        categoryPersistencePort.saveAllWithId(categories);
    }

    @Override
    public void saveColor(KafkaColorList command) {
        List<Color> colors = command.getColors().stream().map(ColorMapper::toDomain).toList();
        colorPersistencePort.saveAllWithId(colors);
    }

    @Override
    public void rollback(Integer itemId) {
        itemDetailPersistencePort.deleteAllWIthItemId(itemId);
        itemImagePersistencePort.deleteAllWithItemId(itemId);
        itemPersistencePort.deleteWithId(itemId);
    }
}
