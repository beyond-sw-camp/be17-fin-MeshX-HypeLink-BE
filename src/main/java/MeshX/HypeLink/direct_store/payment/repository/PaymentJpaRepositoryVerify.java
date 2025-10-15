package MeshX.HypeLink.direct_store.payment.repository;

import MeshX.HypeLink.direct_store.payment.exception.PaymentException;
import MeshX.HypeLink.direct_store.payment.model.entity.PosPayment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static MeshX.HypeLink.direct_store.payment.exception.PaymentExceptionMessage.*;

@Repository
@RequiredArgsConstructor
public class PaymentJpaRepositoryVerify {
    private final PaymentRepository repository;

    public PosPayment save(PosPayment entity) {
        return repository.save(entity);
    }

    public PosPayment findById(Integer id) {
        Optional<PosPayment> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new PaymentException(PAYMENT_NOT_FOUND);
    }

    public PosPayment findByPaymentId(String paymentId) {
        Optional<PosPayment> optional = repository.findByPaymentId(paymentId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new PaymentException(PAYMENT_NOT_FOUND);
    }

    public PosPayment findByOrderId(Integer orderId) {
        Optional<PosPayment> optional = repository.findByOrderId(orderId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new PaymentException(PAYMENT_NOT_FOUND);
    }
}