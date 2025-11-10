package org.example.apidirect.item.adapter.out.mapper;

import org.example.apidirect.item.adapter.out.entity.ColorEntity;
import org.example.apidirect.item.adapter.out.entity.SizeEntity;
import org.example.apidirect.item.adapter.out.entity.StoreItemDetailEntity;
import org.example.apidirect.item.adapter.out.entity.StoreItemEntity;
import org.example.apidirect.item.domain.StoreItemDetail;
import org.example.apidirect.item.usecase.port.in.request.kafka.KafkaItemDetailCommand;

public class ItemDetailMapper {

    public static StoreItemDetail toDomain(StoreItemDetailEntity entity) {
        if (entity == null) return null;

        return StoreItemDetail.builder()
                .id(entity.getId())
                .itemDetailCode(entity.getItemDetailCode())
                .itemCode(entity.getItem() != null ? entity.getItem().getItemCode() : null)
                .storeItemId(entity.getItem() != null ? entity.getItem().getId() : null)
                .colorId(entity.getColor() != null ? entity.getColor().getId() : null)
                .sizeId(entity.getSize() != null ? entity.getSize().getId() : null)
                .color(entity.getColor() != null ? entity.getColor().getColorName() : null)
                .colorCode(entity.getColor() != null ? entity.getColor().getColorCode() : null)
                .size(entity.getSize() != null ? entity.getSize().getSize() : null)
                .stock(entity.getStock())
                .build();
    }

    public static StoreItemDetail toDomain(KafkaItemDetailCommand command) {
        if (command == null) return null;

        return StoreItemDetail.builder()
                .itemDetailCode(command.getItemDetailCode())
                .colorId(command.getColorId())
                .sizeId(command.getSizeId())
                .stock(0)  // 가맹점 재고는 항상 0으로 초기화
                .build();
    }

    public static StoreItemDetailEntity toEntity(StoreItemDetail domain, ColorEntity color, SizeEntity size, StoreItemEntity item) {
        if (domain == null) return null;

        return StoreItemDetailEntity.builder()
                .itemDetailCode(domain.getItemDetailCode())
                .color(color)
                .size(size)
                .stock(domain.getStock())
                .item(item)
                .build();
    }

    // ID만으로 Entity 참조 생성 (lazy loading을 위한 proxy 객체 생성)
    public static StoreItemDetailEntity toEntityWithIds(StoreItemDetail domain, Integer colorId, Integer sizeId, StoreItemEntity item) {
        if (domain == null) return null;

        ColorEntity colorRef = colorId != null ? ColorEntity.builder().id(colorId).build() : null;
        SizeEntity sizeRef = sizeId != null ? SizeEntity.builder().id(sizeId).build() : null;

        return StoreItemDetailEntity.builder()
                .itemDetailCode(domain.getItemDetailCode())
                .color(colorRef)
                .size(sizeRef)
                .stock(domain.getStock())
                .item(item)
                .build();
    }
}
