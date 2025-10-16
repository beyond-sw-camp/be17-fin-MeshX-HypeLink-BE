package MeshX.HypeLink.direct_store.posOrder.repository;

import MeshX.HypeLink.direct_store.posOrder.model.entity.PosOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PosOrderRepository extends JpaRepository<PosOrder, Integer> {
    Optional<PosOrder> findByOrderNumber(String orderNumber);
    List<PosOrder> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<PosOrder> findByMemberId(Integer memberId);
}
