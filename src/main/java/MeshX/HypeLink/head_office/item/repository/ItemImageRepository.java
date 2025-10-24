package MeshX.HypeLink.head_office.item.repository;

import MeshX.HypeLink.head_office.item.model.entity.Item;
import MeshX.HypeLink.head_office.item.model.entity.ItemImage;
import MeshX.HypeLink.image.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemImageRepository extends JpaRepository<ItemImage, Integer> {
    List<ItemImage> findByItemAndIsDeletedIsFalse(Item item);
    Optional<ItemImage> findByItemAndImageAndIsDeletedIsFalse(Item item, Image image);
}
