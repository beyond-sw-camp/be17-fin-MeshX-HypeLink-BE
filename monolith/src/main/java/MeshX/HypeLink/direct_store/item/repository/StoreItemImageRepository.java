package MeshX.HypeLink.direct_store.item.repository;

import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreItemImageRepository extends JpaRepository<StoreItemImage, Integer> {
    @Query("SELECT s FROM StoreItemImage s " +
            "WHERE CONCAT(s.item.store.id, '-', s.originalFilename) IN :keys")
    List<StoreItemImage> findAllByCompositeKeyIn(@Param("keys") List<String> keys);
    List<StoreItemImage> findByItem(StoreItem item);
}
