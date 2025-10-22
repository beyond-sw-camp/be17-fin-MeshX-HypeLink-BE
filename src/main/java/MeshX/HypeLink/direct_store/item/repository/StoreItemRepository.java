package MeshX.HypeLink.direct_store.item.repository;

import MeshX.HypeLink.direct_store.item.model.entity.StoreCategory;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreItemRepository extends JpaRepository<StoreItem, Integer> {
    Optional<StoreItem> findByItemDetailCode(String itemDetailCode);

    List<StoreItem> findByItemCode(String itemCode);

    Page<StoreItem> findByEnName(String enName, Pageable pageable);
    Page<StoreItem> findByKoName(String KoName, Pageable pageable);
    Page<StoreItem> findByCategory(StoreCategory category, Pageable pageable);
}
