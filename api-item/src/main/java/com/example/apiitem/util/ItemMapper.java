package com.example.apiitem.util;

import com.example.apiitem.item.adaptor.out.feign.dto.SaveItemDetailReq;
import com.example.apiitem.item.adaptor.out.feign.dto.SaveItemReq;
import com.example.apiitem.item.adaptor.out.jpa.CategoryEntity;
import com.example.apiitem.item.adaptor.out.jpa.ItemEntity;
import com.example.apiitem.item.domain.Item;
import com.example.apiitem.item.domain.ItemDetail;
import com.example.apiitem.item.domain.ItemImage;
import com.example.apiitem.item.usecase.port.in.request.CreateItemCommand;
import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaItemCommand;
import com.example.apiitem.item.usecase.port.out.response.ItemImageInfoDto;
import com.example.apiitem.item.usecase.port.out.response.ItemInfoDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ItemMapper {
    public static Item toDomain(ItemEntity itemEntity) {
        List<ItemImage> itemImages = Optional.ofNullable(itemEntity.getItemImages())
                .orElse(Collections.emptyList()) // null이면 빈 리스트
                .stream()
                .map(ItemImageMapper::toDomain)
                .toList();

        List<ItemDetail> itemDetails = Optional.ofNullable(itemEntity.getItemDetails())
                .orElse(Collections.emptyList())
                .stream()
                .map(ItemDetailMapper::toDomain)
                .toList();

        return Item.builder()
                .id(itemEntity.getId())
                .itemCode(itemEntity.getItemCode())
                .category(itemEntity.getCategory().getCategory())
                .unitPrice(itemEntity.getUnitPrice())
                .amount(itemEntity.getAmount())
                .enName(itemEntity.getEnName())
                .koName(itemEntity.getKoName())
                .content(itemEntity.getContent())
                .company(itemEntity.getCompany())

                .itemImages(itemImages)
                .itemDetails(itemDetails)
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

    public static SaveItemReq toFeignDto(Item item, List<SaveItemDetailReq> itemDetailReqs) {
        return SaveItemReq.builder()
                .id(item.getId())
                .enName(item.getEnName())
                .koName(item.getKoName())
                .amount(item.getAmount())
                .unitPrice(item.getUnitPrice())
                .category(item.getCategory())
                .itemCode(item.getItemCode())
                .company(item.getCompany())
                .itemDetailList(itemDetailReqs)
                .build();
    }
}
