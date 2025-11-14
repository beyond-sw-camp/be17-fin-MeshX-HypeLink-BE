package com.example.apidirect.payment.adapter.out.persistence;

import com.example.apidirect.payment.adapter.out.entity.PaymentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentsRepository extends JpaRepository<PaymentsEntity, Integer> {
    Optional<PaymentsEntity> findByPaymentId(String paymentId);
    Optional<PaymentsEntity> findByCustomerReceiptId(Integer customerReceiptId);
}
