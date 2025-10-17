package MeshX.HypeLink.head_office.item.repository;

import MeshX.HypeLink.head_office.item.model.entity.Category;
import MeshX.HypeLink.head_office.item.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByItemCode(String itemCode);
    List<Item> findByItemCodeAndIsDeletedIsFalse(String itemCode);
    Optional<Item> findByIdAndIsDeletedIsFalse(Integer id);
    Optional<Item> findByItemDetailCodeAndIsDeletedIsFalse(String itemDetailCode);

    List<Item> findByCategoryAndIsDeletedIsFalse(Category category);
    List<Item> findByEnNameAndIsDeletedIsFalse(String enName);
    List<Item> findByKoNameAndIsDeletedIsFalse(String koName);
    List<Item> findByCompanyAndIsDeletedIsFalse(String company);

    Page<Item> findAllByIsDeletedIsFalse(Pageable pageable);
    Page<Item> findByCategoryContainingAndIsDeletedIsFalse(Category category, Pageable pageable);
    Page<Item> findByEnNameContainingAndIsDeletedIsFalse(String enName, Pageable pageable);
    Page<Item> findByKoNameContainingAndIsDeletedIsFalse(String koName, Pageable pageable);
    Page<Item> findByCompanyContainingAndIsDeletedIsFalse(String company, Pageable pageable);
}
