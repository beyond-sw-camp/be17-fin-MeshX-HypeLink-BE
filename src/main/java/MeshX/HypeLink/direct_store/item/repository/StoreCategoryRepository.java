package MeshX.HypeLink.direct_store.item.repository;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.direct_store.item.model.entity.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreCategoryRepository extends JpaRepository<StoreCategory, Integer> {
    Optional<StoreCategory> findByCategoryAndStore(String category, Store store);
    List<StoreCategory> findAllByCategoryIn(List<String> categories);
}
