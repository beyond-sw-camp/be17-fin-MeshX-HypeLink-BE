package MeshX.HypeLink.head_office.item.repository;

import MeshX.HypeLink.head_office.item.model.entity.Item;
import MeshX.HypeLink.head_office.item.model.entity.ItemImage;
import MeshX.HypeLink.image.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemImageRepository extends JpaRepository<ItemImage, Integer> {
    @Query(value = """
    select ii
    from ItemImage ii
    left join fetch ii.image
    where ii.item = :item
    """)
    List<ItemImage> findByItem(Item item);
    Optional<ItemImage> findByItemAndImage(Item item, Image image);
}
