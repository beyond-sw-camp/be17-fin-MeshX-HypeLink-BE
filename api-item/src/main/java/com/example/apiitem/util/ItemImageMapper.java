package com.example.apiitem.util;

import com.example.apiitem.item.adaptor.out.jpa.ItemEntity;
import com.example.apiitem.item.adaptor.out.jpa.ItemImageEntity;
import com.example.apiitem.item.domain.ItemImage;
import com.example.apiitem.item.usecase.port.in.request.CreateItemImageCommand;
import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaItemImageCommand;

public class ItemImageMapper {
    public static ItemImageEntity toEntity(ItemImage domain, ItemEntity entity) {
        return ItemImageEntity.builder()
                .id(domain.getId())
                .savedPath(domain.getSavedPath())
                .originalFilename(domain.getOriginalFilename())
                .sortIndex(domain.getSortIndex())
                .contentType(domain.getContentType())
                .fileSize(domain.getFileSize())
                .item(entity)
                .build();
    }

    public static ItemImage toDomain(ItemImageEntity entity) {
        return ItemImage.builder()
                .id(entity.getId())
                .savedPath(entity.getSavedPath())
                .sortIndex(entity.getSortIndex())
                .originalFilename(entity.getOriginalFilename())
                .fileSize(entity.getFileSize())
                .contentType(entity.getContentType())
                .build();
    }

    public static ItemImage toDomain(CreateItemImageCommand command) {
        return ItemImage.builder()
                .id(command.getId())
                .savedPath(command.getS3Key())
                .sortIndex(command.getIndex())
                .originalFilename(command.getOriginalFilename())
                .fileSize(command.getFileSize())
                .contentType(command.getContentType())
                .build();
    }

    public static ItemImage toDomain(KafkaItemImageCommand entity) {
        return ItemImage.builder()
                .id(entity.getId())
                .savedPath(entity.getSavedPath())
                .sortIndex(entity.getSortIndex())
                .originalFilename(entity.getOriginalFilename())
                .fileSize(entity.getFileSize())
                .contentType(entity.getContentType())
                .build();
    }
}
