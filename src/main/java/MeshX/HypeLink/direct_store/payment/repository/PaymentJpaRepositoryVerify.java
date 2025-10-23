package MeshX.HypeLink.direct_store.payment.repository;

import MeshX.HypeLink.direct_store.payment.exception.PaymentException;
import MeshX.HypeLink.direct_store.payment.model.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static MeshX.HypeLink.direct_store.payment.exception.PaymentExceptionMessage.*;

@Repository
@RequiredArgsConstructor
public class PaymentJpaRepositoryVerify {
    private final PaymentRepository repository;

    public Payment save(Payment entity) {
        return repository.save(entity);
    }

    public Payment findById(Integer id) {
        Optional<Payment> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new PaymentException(PAYMENT_NOT_FOUND);
    }

    public Payment findByPaymentId(String paymentId) {
        Optional<Payment> optional = repository.findByPaymentId(paymentId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new PaymentException(PAYMENT_NOT_FOUND);
    }

    public Payment findByOrderId(Integer orderId) {
        Optional<Payment> optional = repository.findByOrderId(orderId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new PaymentException(PAYMENT_NOT_FOUND);
    }
}