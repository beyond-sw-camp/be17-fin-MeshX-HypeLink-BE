package MeshX.HypeLink.head_office.item.repository;

import MeshX.HypeLink.head_office.item.model.entity.Category;
import MeshX.HypeLink.head_office.item.model.entity.Item;
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

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByItemCode(String itemCode);
    List<Item> findByItemCodeAndIsDeletedIsFalse(String itemCode);
    Optional<Item> findByIdAndIsDeletedIsFalse(Integer id);
    Optional<Item> findByItemDetailCodeAndIsDeletedIsFalse(String itemDetailCode);

    List<Item> findByCategoryAndIsDeletedIsFalse(Category category);
    List<Item> findByEnNameAndIsDeletedIsFalse(String enName);
    List<Item> findByKoNameAndIsDeletedIsFalse(String koName);
    List<Item> findByCompanyAndIsDeletedIsFalse(String company);

    Page<Item> findAllByIsDeletedIsFalse(Pageable pageable);
    Page<Item> findByCategoryContainingAndIsDeletedIsFalse(Category category, Pageable pageable);
    Page<Item> findByEnNameContainingAndIsDeletedIsFalse(String enName, Pageable pageable);
    Page<Item> findByKoNameContainingAndIsDeletedIsFalse(String koName, Pageable pageable);
    Page<Item> findByCompanyContainingAndIsDeletedIsFalse(String company, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(name = "javax.persistence.lock.timeout", value = "5000") // 5초 대기 후 예외 발생
    })
    @Query("SELECT i FROM Item i WHERE i.id = :id")
    Optional<Item> findByIdForUpdateWithLock(@Param("id") Integer id); // 비관적 락을 적용한 상태

    @Query(
            value = """
        SELECT new MeshX.HypeLink.head_office.order.model.dto.response.PurchaseOrderInfoRes(
            i.id,
            i.koName,
            i.enName,
            c.category,
            col.colorName,
            col.colorCode,
            i.itemCode,
            i.itemDetailCode,
            i.stock,
            CAST(COALESCE(SUM(
                CASE WHEN p.purchaseOrderState = MeshX.HypeLink.head_office.order.model.entity.PurchaseOrderState.REQUESTED
                     THEN p.quantity ELSE 0 END
            ), 0) AS integer)
        )
        FROM Item i
        LEFT JOIN i.category c
        LEFT JOIN i.color col
        LEFT JOIN PurchaseOrder p ON p.item = i
        GROUP BY i.id, i.koName, i.enName, c.category, col.colorName, col.colorCode,
                 i.itemCode, i.itemDetailCode, i.stock
        """,
            countQuery = """
        SELECT COUNT(i)
        FROM Item i
    """
    )
    Page<PurchaseOrderInfoRes> findItemWithRequestedTotalQuantity(Pageable pageable);
}
