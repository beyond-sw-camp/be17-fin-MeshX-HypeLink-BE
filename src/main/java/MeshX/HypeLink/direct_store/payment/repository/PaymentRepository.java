package MeshX.HypeLink.direct_store.payment.repository;

import MeshX.HypeLink.direct_store.payment.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Optional<Payment> findByPaymentId(String paymentId);
    Optional<Payment> findByOrderId(Integer orderId);
}