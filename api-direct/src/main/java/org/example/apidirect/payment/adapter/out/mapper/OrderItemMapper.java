package org.example.apidirect.payment.adapter.out.mapper;

import org.example.apidirect.payment.adapter.out.entity.OrderItemEntity;
import org.example.apidirect.payment.domain.OrderItem;

public class OrderItemMapper {

    public static OrderItem toDomain(OrderItemEntity entity) {
        if (entity == null) return null;

        return OrderItem.builder()
                .id(entity.getId())
                .customerReceiptId(entity.getCustomerReceipt() != null ?
                        entity.getCustomerReceipt().getId() : null)
                .storeItemDetailId(entity.getStoreItemDetailId())
                .quantity(entity.getQuantity())
                .unitPrice(entity.getUnitPrice())
                .totalPrice(entity.getTotalPrice())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static OrderItemEntity toEntity(OrderItem domain) {
        if (domain == null) return null;

        return OrderItemEntity.builder()
                .id(domain.getId())
                .storeItemDetailId(domain.getStoreItemDetailId())
                .quantity(domain.getQuantity())
                .unitPrice(domain.getUnitPrice())
                .totalPrice(domain.getTotalPrice())
                .build();
    }
}
