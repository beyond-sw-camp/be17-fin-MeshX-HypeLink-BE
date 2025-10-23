package MeshX.HypeLink.head_office.item.repository;

import MeshX.HypeLink.head_office.item.model.entity.Item;
import MeshX.HypeLink.head_office.item.model.entity.ItemDetail;
import MeshX.HypeLink.head_office.order.model.dto.response.PurchaseOrderInfoRes;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemDetailRepository extends JpaRepository<ItemDetail, Integer> {
    Optional<ItemDetail> findByItemDetailCode(String itemDetailCode);
    List<ItemDetail> findByItem(Item item);
    @Query("SELECT d FROM ItemDetail d JOIN FETCH d.item i WHERE i.id = :id")
    List<ItemDetail> findByItemId(Integer id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(name = "javax.persistence.lock.timeout", value = "5000") // 5초 대기 후 예외 발생
    })
    @Query("SELECT d FROM ItemDetail d WHERE d.id = :id")
    Optional<ItemDetail> findByIdForUpdateWithLock(@Param("id") Integer id); // 비관적 락을 적용한 상태

    @Query(
            value = """
        SELECT new MeshX.HypeLink.head_office.order.model.dto.response.PurchaseOrderInfoRes(
            d.id,
            i.koName,
            i.enName,
            c.category,
            col.colorName,
            col.colorCode,
            i.itemCode,
            d.itemDetailCode,
            d.stock,
            CAST(COALESCE(SUM(
                CASE WHEN p.purchaseOrderState = MeshX.HypeLink.head_office.order.model.entity.PurchaseOrderState.REQUESTED
                     THEN p.quantity ELSE 0 END
            ), 0) AS integer)
        )
        FROM ItemDetail d
        LEFT JOIN d.item i
        LEFT JOIN i.category c
        LEFT JOIN d.color col
        LEFT JOIN PurchaseOrder p ON p.itemDetail = d
        GROUP BY i.id, i.koName, i.enName, c.category, col.colorName, col.colorCode,
                 i.itemCode, d.itemDetailCode, d.stock
        """,
            countQuery = """
        SELECT COUNT(d)
        FROM ItemDetail d
    """
    )
    Page<PurchaseOrderInfoRes> findItemDetailWithRequestedTotalQuantity(Pageable pageable);
}
