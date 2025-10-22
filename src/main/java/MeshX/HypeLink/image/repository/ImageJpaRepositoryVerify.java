package MeshX.HypeLink.image.repository;

import MeshX.HypeLink.image.exception.ImageException;
import MeshX.HypeLink.image.model.entity.Image;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.image.exception.ImageExceptionType.IMAGE_NOT_FOUND;
import static MeshX.HypeLink.image.exception.ImageExceptionType.INVALID_IMAGE_IDX;

@Repository
@AllArgsConstructor
public class ImageJpaRepositoryVerify {
    private ImageRepository repository;

    public void create(Image entity) {
        repository.save(entity);
    }

    public Image findById(Image entity) {
        Optional<Image> result = repository.findById(entity.getId());
        if (result.isPresent()) {
            return result.get();
        }
        throw new ImageException(INVALID_IMAGE_IDX);
    }

    public List<Image> findAll(Image entity) {
        List<Image> results = repository.findAll();
        if (!results.isEmpty()) {
            return results;
        }

        throw new ImageException(IMAGE_NOT_FOUND);
    }

    public void update (Image entity) {
        repository.save(entity);
    }

    public void delete (Image entity) {
        repository.delete(entity);
    }
}
