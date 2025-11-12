package org.example.apidirect.payment.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.payment.adapter.out.entity.OrderItemEntity;
import org.example.apidirect.payment.adapter.out.mapper.OrderItemMapper;
import org.example.apidirect.payment.domain.OrderItem;
import org.example.apidirect.payment.usecase.port.out.OrderItemPersistencePort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class OrderItemPersistenceAdaptor implements OrderItemPersistencePort {

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem save(OrderItem orderItem) {
        OrderItemEntity entity = OrderItemMapper.toEntity(orderItem);
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
