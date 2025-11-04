package MeshX.HypeLink.head_office.order.repository;

import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrder;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

    @Query("SELECT po FROM PurchaseOrder po " +
            "JOIN FETCH po.requester " +
            "JOIN FETCH po.supplier " +
            "WHERE po.purchaseOrderState = :state")
    List<PurchaseOrder> findAllByPurchaseOrderState(PurchaseOrderState state);
}
