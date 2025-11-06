package com.example.apiitem.util;

import com.example.apiitem.item.adaptor.out.jpa.CategoryEntity;
import com.example.apiitem.item.adaptor.out.jpa.ItemEntity;
import com.example.apiitem.item.domain.Item;
import com.example.apiitem.item.usecase.port.in.request.CreateItemCommand;
import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaItemCommand;
import com.example.apiitem.item.usecase.port.out.response.ItemImageInfoDto;
import com.example.apiitem.item.usecase.port.out.response.ItemInfoDto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ItemMapper {
    public static Item toDomain(ItemEntity itemEntity) {
        return Item.builder()
                .id(itemEntity.getId())
                .itemCode(itemEntity.getItemCode())
                .unitPrice(itemEntity.getUnitPrice())
                .amount(itemEntity.getAmount())
                .enName(itemEntity.getEnName())
                .koName(itemEntity.getKoName())
                .content(itemEntity.getContent())
                .company(itemEntity.getCompany())
                .build();
    }

    public static Item toDomain(CreateItemCommand command) {
        return Item.builder()
                .itemCode(command.getItemCode())
                .unitPrice(command.getUnitPrice())
                .category(command.getCategory())
                .amount(command.getAmount())
                .enName(command.getEnName())
                .koName(command.getKoName())
                .content(command.getContent())
                .company(command.getCompany())
                .build();
    }

    public static Item toDomain(KafkaItemCommand command) {
        return Item.builder()
                .id(command.getId())
                .unitPrice(command.getUnitPrice())
                .itemCode(command.getItemCode())
                .amount(command.getAmount())
                .enName(command.getEnName())
                .koName(command.getKoName())
                .content(command.getContent())
                .company(command.getCompany())
                .categoryId(command.getCategory())
                .build();
    }

    public static Item toDomain(Integer id) {
        return Item.builder()
                .id(id)
                .build();
    }

    public static Item toDomain(String itemCode) {
        return Item.builder()
                .itemCode(itemCode)
                .build();
    }

    public static ItemEntity toEntity(CategoryEntity categoryEntity, Item item) {
        return ItemEntity.builder()
                .id(item.getId())
                .category(categoryEntity)
                .itemCode(item.getItemCode())
                .unitPrice(item.getUnitPrice())
                .amount(item.getAmount())
                .enName(item.getEnName())
                .koName(item.getKoName())
                .content(item.getContent())
                .company(item.getCompany())
                .build();
    }

    public static ItemInfoDto toDto(Item item, Function<String, String> urlGenerator) {
        List<ItemImageInfoDto> imageDtos = item.getItemImages().stream()
                .map(image -> ItemImageInfoDto.builder()
                        .id(image.getId())
                        .originalName(image.getOriginalFilename())
                        .imageUrl(urlGenerator.apply(image.getSavedPath()))
                        .imageSize(image.getFileSize())
                        .index(image.getSortIndex())
                        .originalFilename(image.getOriginalFilename())
                        .build())
                .collect(Collectors.toList());

        return ItemInfoDto.builder()
                .id(item.getId())
                .enName(item.getEnName())
                .koName(item.getKoName())
                .category(item.getCategory())
                .amount(item.getAmount())
                .unitPrice(item.getUnitPrice())
                .content(item.getContent())
                .company(item.getCompany())
                .itemCode(item.getItemCode())
                .images(imageDtos)
                .build();
    }
}
