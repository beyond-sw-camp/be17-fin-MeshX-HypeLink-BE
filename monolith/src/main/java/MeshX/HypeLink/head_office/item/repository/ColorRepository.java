package MeshX.HypeLink.head_office.item.repository;

import MeshX.HypeLink.head_office.item.model.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<Color, Integer>  {
    Optional<Color> findByColorName(String colorName);
    Optional<Color> findByIdAndIsDeletedIsFalse(Integer id);
    Optional<Color> findByColorNameAndIsDeletedIsFalse(String colorName);
}
