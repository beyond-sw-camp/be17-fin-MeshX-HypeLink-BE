package MeshX.HypeLink.image.repository;

import MeshX.HypeLink.image.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Optional<Image> findByOriginalFilename(String originalFilename);
}
