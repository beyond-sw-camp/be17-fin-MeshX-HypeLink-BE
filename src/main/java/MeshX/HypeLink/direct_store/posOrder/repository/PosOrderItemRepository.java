package MeshX.HypeLink.direct_store.posOrder.repository;

import MeshX.HypeLink.direct_store.pos.posOrder.model.entity.PosOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosOrderItemRepository extends JpaRepository<PosOrderItem, Integer> {
}
