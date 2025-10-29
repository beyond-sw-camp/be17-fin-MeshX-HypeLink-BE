package MeshX.HypeLink.head_office.item.repository;

import MeshX.HypeLink.head_office.item.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByCategory(String category);
    Optional<Category> findByIdAndIsDeletedIsFalse(Integer id);
    Optional<Category> findByCategoryAndIsDeletedIsFalse(String colorName);
}
