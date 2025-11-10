package org.example.apidirect.item.usecase.service;

import MeshX.common.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaCategoryList;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaColorList;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaItemDto;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaSizeList;
import org.example.apidirect.item.domain.Color;
import org.example.apidirect.item.domain.Size;
import org.example.apidirect.item.domain.StoreItem;
import org.example.apidirect.item.usecase.port.in.KafkaPort;
import org.example.apidirect.item.usecase.port.in.command.SyncCategoryCommand;
import org.example.apidirect.item.usecase.port.in.command.SyncColorCommand;
import org.example.apidirect.item.usecase.port.in.command.SyncItemCommand;
import org.example.apidirect.item.usecase.port.in.command.SyncSizeCommand;
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
    public void saveItem(KafkaItemDto dto) {
        // Kafka DTO → Command → Domain
        SyncItemCommand command = SyncItemCommand.toCommand(dto);
        StoreItem item = command.toDomain();

        // 저장 (upsert)
        itemPersistencePort.save(item);
    }

    @Override
    public void saveCategories(KafkaCategoryList dto) {
        // Kafka DTO → Command → Domain
        SyncCategoryCommand command = SyncCategoryCommand.toCommand(dto);
        List<String> categories = command.getCategories();

        // 저장
        categoryPersistencePort.saveAll(categories);
    }

    @Override
    public void saveColors(KafkaColorList dto) {
        // Kafka DTO → Command → Domain
        List<SyncColorCommand> commands = dto.getColors().stream()
                .map(SyncColorCommand::toCommand)
                .collect(Collectors.toList());

        List<Color> colors = commands.stream()
                .map(SyncColorCommand::toDomain)
                .collect(Collectors.toList());

        // 저장
        colorPersistencePort.saveAll(colors);
    }

    @Override
    public void saveSizes(KafkaSizeList dto) {
        // Kafka DTO → Command → Domain
        List<SyncSizeCommand> commands = dto.getSizes().stream()
                .map(SyncSizeCommand::toCommand)
                .collect(Collectors.toList());

        List<Size> sizes = commands.stream()
                .map(SyncSizeCommand::toDomain)
                .collect(Collectors.toList());

        // 저장
        sizePersistencePort.saveAll(sizes);
    }
}
