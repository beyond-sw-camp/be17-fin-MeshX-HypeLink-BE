package org.example.apidirect.item.adapter.out.mapper;

import org.example.apidirect.item.adapter.out.entity.StoreItemEntity;
import org.example.apidirect.item.domain.StoreItem;
import org.example.apidirect.item.usecase.port.in.request.kafka.KafkaItemCommand;

import java.util.List;
import java.util.stream.Collectors;

public class ItemMapper {

    public static StoreItem toDomain(StoreItemEntity entity) {
        if (entity == null) return null;

        return StoreItem.builder()
                .id(entity.getId())
                .itemCode(entity.getItemCode())
                .unitPrice(entity.getUnitPrice())
                .amount(entity.getAmount())
                .enName(entity.getEnName())
                .koName(entity.getKoName())
                .content(entity.getContent())
                .company(entity.getCompany())
                .category(entity.getCategory())
                .itemDetails(entity.getItemDetails().stream()
                        .map(ItemDetailMapper::toDomain)
                        .collect(Collectors.toList()))
                .itemImages(entity.getItemImages().stream()
                        .map(ItemImageMapper::toDomain)
                        .collect(Collectors.toList()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static StoreItem toDomain(KafkaItemCommand command) {
        if (command == null) return null;

        return StoreItem.builder()
                .id(command.getId())
                .itemCode(command.getItemCode())
                .unitPrice(command.getUnitPrice())
                .amount(command.getAmount())
                .enName(command.getEnName())
                .koName(command.getKoName())
                .content(command.getContent())
                .company(command.getCompany())
                .category(String.valueOf(command.getCategory()))
                .itemDetails(command.getItemDetails() != null ? command.getItemDetails().stream()
                        .map(ItemDetailMapper::toDomain)
                        .collect(Collectors.toList()) : List.of())
                .itemImages(command.getItemImages() != null ? command.getItemImages().stream()
                        .map(ItemImageMapper::toDomain)
                        .collect(Collectors.toList()) : List.of())
//                .createdAt(command.getCreatedAt())
//                .updatedAt(command.getUpdatedAt())
                .build();
    }

    public static StoreItemEntity toEntity(StoreItem domain) {
        if (domain == null) return null;

        return StoreItemEntity.builder()
                .id(domain.getId())
                .itemCode(domain.getItemCode())
                .unitPrice(domain.getUnitPrice())
                .amount(domain.getAmount())
                .enName(domain.getEnName())
                .koName(domain.getKoName())
                .content(domain.getContent())
                .company(domain.getCompany())
                .category(domain.getCategory())
                .build();
    }
}
