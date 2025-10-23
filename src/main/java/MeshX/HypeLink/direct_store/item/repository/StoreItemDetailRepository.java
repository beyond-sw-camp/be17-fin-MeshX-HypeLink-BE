package MeshX.HypeLink.direct_store.item.repository;

import MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreItemDetailRepository extends JpaRepository<StoreItemDetail, Integer> {
    List<StoreItemDetail> findAllByItemDetailCodeIn(List<String> itemDetailCodes);
}
