package com.example.apidirect.payment.usecase.port.out;

import com.example.apidirect.payment.domain.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemPersistencePort {
    OrderItem save(OrderItem orderItem);
    List<OrderItem> findByCustomerReceiptId(Integer customerReceiptId);
    Optional<OrderItem> findById(Integer id);
}
