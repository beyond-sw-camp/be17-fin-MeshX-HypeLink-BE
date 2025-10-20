package MeshX.HypeLink.image.repository;

import MeshX.HypeLink.image.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
