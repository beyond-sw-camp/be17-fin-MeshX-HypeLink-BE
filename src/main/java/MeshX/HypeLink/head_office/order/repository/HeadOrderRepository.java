package MeshX.HypeLink.head_office.order.repository;

import MeshX.HypeLink.head_office.order.model.entity.HeadOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeadOrderRepository extends JpaRepository<HeadOrder, Integer> {
}
