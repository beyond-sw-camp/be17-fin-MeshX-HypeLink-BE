package MeshX.HypeLink.head_office.item.repository;

import MeshX.HypeLink.head_office.item.model.entity.Category;
import MeshX.HypeLink.head_office.item.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Optional<Item> findByItemCode(String itemCode);
    Optional<Item> findById(Integer id);

    @Query("""
        SELECT DISTINCT i
        FROM Item i
        LEFT JOIN FETCH i.itemImages ii
        LEFT JOIN FETCH ii.image
        """)
    List<Item> findAllWithImage();
    List<Item> findByCategory(Category category);
    List<Item> findByEnName(String enName);
    List<Item> findByKoName(String koName);
    List<Item> findByCompany(String company);

    @EntityGraph(attributePaths = {
            "category",
            "itemDetails.color",
            "itemDetails.size"
    })
    @Query("SELECT DISTINCT i FROM Item i")
    Page<Item> findAllWithImages(Pageable pageable);
    Page<Item> findByCategory(Category category, Pageable pageable);
    Page<Item> findByEnName(String enName, Pageable pageable);
    Page<Item> findByKoName(String koName, Pageable pageable);
    Page<Item> findByCompany(String company, Pageable pageable);
}
