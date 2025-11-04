package MeshX.HypeLink.direct_store.payment.repository;

import MeshX.HypeLink.direct_store.payment.exception.PaymentException;
import MeshX.HypeLink.direct_store.payment.model.entity.Payments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static MeshX.HypeLink.direct_store.payment.exception.PaymentExceptionMessage.*;

@Repository
@RequiredArgsConstructor
public class PaymentJpaRepositoryVerify {
    private final PaymentRepository repository;

    public Payments save(Payments entity) {
        return repository.save(entity);
    }

    public Payments findById(Integer id) {
        Optional<Payments> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new PaymentException(PAYMENT_NOT_FOUND);
    }
}