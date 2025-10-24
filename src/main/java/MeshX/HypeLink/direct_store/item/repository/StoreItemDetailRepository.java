package MeshX.HypeLink.direct_store.item.repository;

import MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreItemDetailRepository extends JpaRepository<StoreItemDetail, Integer> {
    List<StoreItemDetail> findAllByItemDetailCodeIn(List<String> itemDetailCodes);

    @Query("SELECT s FROM StoreItemDetail s WHERE CONCAT(s.item.store.id, '-', s.itemDetailCode) IN :keys")
    List<StoreItemDetail> findAllByCompositeKeyIn(@Param("keys") List<String> keys);
}
