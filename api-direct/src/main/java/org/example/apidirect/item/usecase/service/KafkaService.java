package org.example.apidirect.item.usecase.service;

import MeshX.common.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apidirect.item.adapter.out.mapper.ItemMapper;
import org.example.apidirect.item.domain.Color;
import org.example.apidirect.item.domain.Size;
import org.example.apidirect.item.domain.StoreItem;
import org.example.apidirect.item.usecase.port.in.KafkaPort;
import org.example.apidirect.item.usecase.port.in.request.kafka.*;
import org.example.apidirect.item.usecase.port.out.CategoryPersistencePort;
import org.example.apidirect.item.usecase.port.out.ColorPersistencePort;
import org.example.apidirect.item.usecase.port.out.ItemPersistencePort;
import org.example.apidirect.item.usecase.port.out.SizePersistencePort;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class KafkaService implements KafkaPort {

    private final ItemPersistencePort itemPersistencePort;
    private final CategoryPersistencePort categoryPersistencePort;
    private final ColorPersistencePort colorPersistencePort;
    private final SizePersistencePort sizePersistencePort;

    @Override
    public void saveItem(KafkaItemCommand command) {
        // Kafka Command → Domain (Mapper 사용)
        StoreItem item = ItemMapper.toDomain(command);

        // 저장 (upsert)
        itemPersistencePort.save(item);
    }

    @Override
    public void saveCategories(KafkaCategoryListCommand command) {
        // Kafka Command → Domain
        List<String> categories = command.getCategories().stream()
                .map(KafkaCategoryCommand::getCategory)
                .collect(Collectors.toList());

        // 저장
        categoryPersistencePort.saveAll(categories);
    }

    @Override
    public void saveColors(KafkaColorListCommand command) {
        // Kafka Command → Domain
        List<Color> colors = command.getColors().stream()
                .map(colorCommand -> Color.builder()
                        .id(colorCommand.getId())
                        .colorName(colorCommand.getColorName())
                        .colorCode(colorCommand.getColorCode())
                        .build())
                .collect(Collectors.toList());

        // 저장
        colorPersistencePort.saveAll(colors);
    }

    @Override
    public void saveSizes(KafkaSizeListCommand command) {
        // Kafka Command → Domain
        List<Size> sizes = command.getSizes().stream()
                .map(sizeCommand -> Size.builder()
                        .id(sizeCommand.getId())
                        .size(sizeCommand.getSize())
                        .build())
                .collect(Collectors.toList());

        // 저장
        sizePersistencePort.saveAll(sizes);
    }
}
