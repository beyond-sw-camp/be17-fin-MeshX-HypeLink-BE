package MeshX.HypeLink.direct_store.order.repository;

import MeshX.HypeLink.direct_store.order.model.entity.DirectOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectOrderRepository extends JpaRepository<DirectOrder, Integer> {
}
