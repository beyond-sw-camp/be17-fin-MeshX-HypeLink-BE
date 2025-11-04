package MeshX.HypeLink.head_office.item.repository;

import MeshX.HypeLink.head_office.item.model.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SizeRepository extends JpaRepository<Size, Integer> {
    Optional<Size> findBySize(String size);
    Optional<Size> findByIdAndIsDeletedIsFalse(Integer id);
    Optional<Size> findBySizeAndIsDeletedIsFalse(String size);
}
