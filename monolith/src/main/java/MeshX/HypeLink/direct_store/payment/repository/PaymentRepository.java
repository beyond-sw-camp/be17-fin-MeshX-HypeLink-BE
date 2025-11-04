package MeshX.HypeLink.direct_store.payment.repository;

import MeshX.HypeLink.direct_store.payment.model.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payments, Integer> {
}