package com.example.apiitem.util;

import com.example.apiitem.item.adaptor.out.jpa.ColorEntity;
import com.example.apiitem.item.adaptor.out.jpa.ItemDetailEntity;
import com.example.apiitem.item.adaptor.out.jpa.ItemEntity;
import com.example.apiitem.item.adaptor.out.jpa.SizeEntity;
import com.example.apiitem.item.domain.ItemDetail;
import com.example.apiitem.item.usecase.port.in.request.CreateItemDetailCommand;
import com.example.apiitem.item.usecase.port.in.request.CreateItemDetailsCommand;
import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaItemDetailCommand;
import com.example.apiitem.item.usecase.port.out.response.ItemAndItemDetailInfoDto;
import com.example.apiitem.item.usecase.port.out.response.ItemDetailInfoDto;
import com.example.apiitem.item.usecase.port.out.response.ItemDetailsInfoListDto;

import java.util.List;

public class ItemDetailMapper {
    public static ItemDetailEntity toEntity(ItemDetail domain, ColorEntity color, SizeEntity size, ItemEntity item) {
        return ItemDetailEntity.builder()
                .id(domain.getId())
                .itemDetailCode(domain.getItemDetailCode())
                .stock(domain.getStock())
                .color(color)
                .size(size)
                .item(item)
                .build();
    }

    public static ItemDetail toDomain(ItemDetailEntity entity) {
        return ItemDetail.builder()
                .id(entity.getId())
                .categoryId(entity.getItem().getCategory().getId())
                .category(entity.getItem().getCategory().getCategory())
                .amount(entity.getItem().getAmount())
                .unitPrice(entity.getItem().getUnitPrice())
                .enName(entity.getItem().getEnName())
                .koName(entity.getItem().getKoName())
                .content(entity.getItem().getContent())
                .company(entity.getItem().getCompany())
                .itemId(entity.getItem().getId())
                .itemCode(entity.getItem().getItemCode())

                .colorId(entity.getColor().getId())
                .colorCode(entity.getColor().getColorCode())
                .colorName(entity.getColor().getColorName())
                .sizeId(entity.getSize().getId())
                .size(entity.getSize().getSize())
                .itemDetailCode(entity.getItemDetailCode())
                .stock(entity.getStock())
                .build();
    }

    // itemDetail Entity만 들고오는경우 (+ Size, Color)
    public static ItemDetail toSimpleDomain(ItemDetailEntity entity) {
        return ItemDetail.builder()
                .id(entity.getId())
                .colorId(entity.getColor().getId())
                .colorCode(entity.getColor().getColorCode())
                .colorName(entity.getColor().getColorName())
                .sizeId(entity.getSize().getId())
                .size(entity.getSize().getSize())
                .itemDetailCode(entity.getItemDetailCode())
                .stock(entity.getStock())
                .build();
    }

    public static List<ItemDetail> toDomains(CreateItemDetailsCommand command) {
        return command.getDetails().stream()
                        .map(one ->
                                ItemDetail.builder()
                                        .itemId(command.getItemId())
                                        .itemDetailCode(one.getItemDetailCode())
                                        .stock(one.getStock())
                                        .colorName(one.getColor())
                                        .size(one.getSize())
                                        .build()
                        )
                        .toList();
    }

    public static ItemDetail toDomain(CreateItemDetailCommand command) {
        return ItemDetail.builder()
                .itemDetailCode(command.getItemDetailCode())
                .stock(command.getStock())
                .colorName(command.getColor())
                .size(command.getSize())
                .build();
    }

    public static ItemDetail toDomain(KafkaItemDetailCommand command) {
        return ItemDetail.builder()
                .itemDetailCode(command.getItemDetailCode())
                .stock(command.getStock())
                .colorId(command.getColorId())
                .sizeId(command.getSizeId())
                .build();
    }

    public static ItemAndItemDetailInfoDto toDto(ItemDetail domain) {
        return ItemAndItemDetailInfoDto.builder()
                .id(domain.getId())
                .category(domain.getCategory())
                .color(domain.getColorName())
                .colorCode(domain.getColorCode())
                .size(domain.getSize())
                .amount(domain.getAmount())
                .unitPrice(domain.getUnitPrice())
                .enName(domain.getEnName())
                .koName(domain.getKoName())
                .content(domain.getContent())
                .company(domain.getCompany())
                .itemCode(domain.getItemCode())
                .itemDetailCode(domain.getItemDetailCode())
                .stock(domain.getStock())
                .build();
    }

    public static ItemDetailsInfoListDto toDto(List<ItemDetail> domains) {
        List<ItemDetailInfoDto> dtos = domains.stream()
                .map(ItemDetailMapper::toSimpleDto)
                .toList();

        return ItemDetailsInfoListDto.builder()
                .itemInfoResList(dtos)
                .build();
    }

    public static ItemDetailInfoDto toSimpleDto(ItemDetail domain) {
        return ItemDetailInfoDto.builder()
                .id(domain.getId())
                .color(domain.getColorName())
                .colorCode(domain.getColorCode())
                .itemDetailCode(domain.getItemDetailCode())
                .size(domain.getSize())
                .build();
    }
}
