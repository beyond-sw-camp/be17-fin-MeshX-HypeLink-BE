package com.example.apiitem.item.domain;

import com.example.apiitem.item.adaptor.out.jpa.ItemEntity;
import com.example.apiitem.item.adaptor.out.jpa.ItemImageEntity;
import com.example.apiitem.item.usecase.port.in.request.CreateItemImageCommand;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemImage {
    private Integer id;
    private Integer itemId;
    private Integer sortIndex;
    private String originalFilename;
    private String savedPath;
    private String contentType;
    private Long fileSize;

    public ItemImageEntity toEntity(ItemEntity item) {
        return ItemImageEntity.builder()
                .item(item)
                .sortIndex(sortIndex)
                .originalFilename(originalFilename)
                .savedPath(savedPath)
                .contentType(contentType)
                .fileSize(fileSize)
                .build();
    }

    public static ItemImage toDomain(ItemImageEntity itemImageEntity) {
        return ItemImage.builder()
                .id(itemImageEntity.getId())
                .itemId(itemImageEntity.getItem().getId())
                .sortIndex(itemImageEntity.getSortIndex())
                .originalFilename(itemImageEntity.getOriginalFilename())
                .savedPath(itemImageEntity.getSavedPath())
                .contentType(itemImageEntity.getContentType())
                .fileSize(itemImageEntity.getFileSize())
                .build();
    }

    public static ItemImage toDomain(CreateItemImageCommand command) {
        return ItemImage.builder()
                .itemId(command.getId())
                .sortIndex(command.getIndex())
                .originalFilename(command.getOriginalFilename())
                .savedPath(command.getS3Key())
                .contentType(command.getContentType())
                .fileSize(command.getFileSize())
                .build();
    }
}
