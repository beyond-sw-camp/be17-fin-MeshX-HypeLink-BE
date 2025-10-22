package MeshX.HypeLink.head_office.item.repository;

import MeshX.HypeLink.head_office.item.model.entity.Category;
import MeshX.HypeLink.head_office.item.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Optional<Item> findByItemCode(String itemCode);
    Optional<Item> findById(Integer id);

    List<Item> findByCategory(Category category);
    List<Item> findByEnName(String enName);
    List<Item> findByKoName(String koName);
    List<Item> findByCompany(String company);

    Page<Item> findAll(Pageable pageable);
    Page<Item> findByCategory(Category category, Pageable pageable);
    Page<Item> findByEnName(String enName, Pageable pageable);
    Page<Item> findByKoName(String koName, Pageable pageable);
    Page<Item> findByCompany(String company, Pageable pageable);
}
