package org.example.apidirect.payment.adapter.out.persistence;

import org.example.apidirect.payment.adapter.out.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {
    List<OrderItemEntity> findByCustomerReceiptId(Integer customerReceiptId);
}
