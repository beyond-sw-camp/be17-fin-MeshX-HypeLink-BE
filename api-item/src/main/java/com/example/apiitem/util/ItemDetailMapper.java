package com.example.apiitem.util;

import com.example.apiitem.item.adaptor.out.jpa.ColorEntity;
import com.example.apiitem.item.adaptor.out.jpa.ItemDetailEntity;
import com.example.apiitem.item.adaptor.out.jpa.ItemEntity;
import com.example.apiitem.item.adaptor.out.jpa.SizeEntity;
import com.example.apiitem.item.domain.ItemDetail;
import com.example.apiitem.item.usecase.port.in.request.CreateItemDetailCommand;
import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaItemDetailCommand;

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
}
