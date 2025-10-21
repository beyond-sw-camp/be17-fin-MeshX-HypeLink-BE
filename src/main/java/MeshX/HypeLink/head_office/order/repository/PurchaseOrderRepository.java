package MeshX.HypeLink.head_office.order.repository;

import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {
}
