package MeshX.HypeLink.direct_store.item.repository;

import MeshX.HypeLink.direct_store.item.model.entity.StoreItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreItemImageRepository extends JpaRepository<StoreItemImage, Integer> {
    List<StoreItemImage> findAllBySavedPathIn(List<String> savedPaths);
}
