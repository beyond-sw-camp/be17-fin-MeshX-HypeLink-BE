package MeshX.HypeLink.direct_store.payment.repository;

import MeshX.HypeLink.direct_store.payment.model.entity.PosPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PosPayment, Integer> {
    Optional<PosPayment> findByPaymentId(String paymentId);
    Optional<PosPayment> findByOrderId(Integer orderId);
}