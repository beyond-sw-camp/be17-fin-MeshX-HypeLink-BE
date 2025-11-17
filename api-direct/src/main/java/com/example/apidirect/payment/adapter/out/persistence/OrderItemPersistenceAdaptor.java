package com.example.apidirect.payment.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import com.example.apidirect.payment.adapter.out.entity.CustomerReceiptEntity;
import com.example.apidirect.payment.adapter.out.entity.OrderItemEntity;
import com.example.apidirect.payment.adapter.out.mapper.OrderItemMapper;
import com.example.apidirect.payment.domain.OrderItem;
import com.example.apidirect.payment.usecase.port.out.OrderItemPersistencePort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class OrderItemPersistenceAdaptor implements OrderItemPersistencePort {

    private final OrderItemRepository orderItemRepository;
    private final CustomerReceiptRepository customerReceiptRepository;

    @Override
    public OrderItem save(OrderItem orderItem) {
        OrderItemEntity entity = OrderItemMapper.toEntity(orderItem);

        // CustomerReceipt 설정
        if (orderItem.getCustomerReceiptId() != null) {
            CustomerReceiptEntity receipt = customerReceiptRepository.findById(orderItem.getCustomerReceiptId())
                    .orElseThrow(() -> new RuntimeException("CustomerReceipt not found"));
            entity.setCustomerReceipt(receipt);
        }

        OrderItemEntity saved = orderItemRepository.save(entity);
        return OrderItemMapper.toDomain(saved);
    }

    @Override
    public List<OrderItem> findByCustomerReceiptId(Integer customerReceiptId) {
        return orderItemRepository.findByCustomerReceiptId(customerReceiptId).stream()
                .map(OrderItemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderItem> findById(Integer id) {
        return orderItemRepository.findById(id)
                .map(OrderItemMapper::toDomain);
    }
}
