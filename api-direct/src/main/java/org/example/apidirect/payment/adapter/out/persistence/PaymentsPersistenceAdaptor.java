package org.example.apidirect.payment.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.payment.adapter.out.entity.PaymentsEntity;
import org.example.apidirect.payment.adapter.out.mapper.PaymentsMapper;
import org.example.apidirect.payment.domain.Payments;
import org.example.apidirect.payment.usecase.port.out.PaymentsPersistencePort;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class PaymentsPersistenceAdaptor implements PaymentsPersistencePort {

    private final PaymentsRepository paymentsRepository;

    @Override
    public Payments save(Payments payments) {
        PaymentsEntity entity = PaymentsMapper.toEntity(payments);
        PaymentsEntity saved = paymentsRepository.save(entity);
        return PaymentsMapper.toDomain(saved);
    }

    @Override
    public Optional<Payments> findByPaymentId(String paymentId) {
        return paymentsRepository.findByPaymentId(paymentId)
                .map(PaymentsMapper::toDomain);
    }

    @Override
    public Optional<Payments> findByCustomerReceiptId(Integer customerReceiptId) {
        return paymentsRepository.findByCustomerReceiptId(customerReceiptId)
                .map(PaymentsMapper::toDomain);
    }

    @Override
    public Optional<Payments> findById(Integer id) {
        return paymentsRepository.findById(id)
                .map(PaymentsMapper::toDomain);
    }
}
