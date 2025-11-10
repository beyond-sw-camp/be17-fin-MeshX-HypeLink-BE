package org.example.apidirect.item.adapter.out.mapper;

import org.example.apidirect.item.adapter.out.entity.StoreItemEntity;
import org.example.apidirect.item.adapter.out.entity.StoreItemImageEntity;
import org.example.apidirect.item.domain.StoreItemImage;
import org.example.apidirect.item.usecase.port.in.request.kafka.KafkaItemImageCommand;

public class ItemImageMapper {

    public static StoreItemImage toDomain(StoreItemImageEntity entity) {
        if (entity == null) return null;

        return StoreItemImage.builder()
                .id(entity.getId())
                .sortIndex(entity.getSortIndex())
                .originalFilename(entity.getOriginalFilename())
                .savedPath(entity.getSavedPath())
                .contentType(entity.getContentType())
                .fileSize(entity.getFileSize())
                .build();
    }

    public static StoreItemImage toDomain(KafkaItemImageCommand command) {
        if (command == null) return null;

        return StoreItemImage.builder()
                .sortIndex(command.getSortIndex())
                .originalFilename(command.getOriginalFilename())
                .savedPath(command.getSavedPath())
                .contentType(command.getContentType())
                .fileSize(command.getFileSize())
                .build();
    }

    public static StoreItemImageEntity toEntity(StoreItemImage domain, StoreItemEntity item) {
        if (domain == null) return null;

        return StoreItemImageEntity.builder()
                .sortIndex(domain.getSortIndex())
                .originalFilename(domain.getOriginalFilename())
                .savedPath(domain.getSavedPath())
                .contentType(domain.getContentType())
                .fileSize(domain.getFileSize())
                .item(item)
                .build();
    }
}
