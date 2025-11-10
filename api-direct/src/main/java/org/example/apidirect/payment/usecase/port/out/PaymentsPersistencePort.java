package org.example.apidirect.payment.usecase.port.out;

import org.example.apidirect.payment.domain.Payments;

import java.util.Optional;

public interface PaymentsPersistencePort {
    Payments save(Payments payments);
    Optional<Payments> findByPaymentId(String paymentId);
    Optional<Payments> findByCustomerReceiptId(Integer customerReceiptId);
    Optional<Payments> findById(Integer id);
}
