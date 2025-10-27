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

    public Image save(Image entity) {
        return repository.save(entity);
    }

    public Image findById(Integer id) {
        Optional<Image> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ImageException(INVALID_IMAGE_IDX);
    }

    public Image findByOriginalFilename(String originalFilename) {
        Optional<Image> result = repository.findByOriginalFilename(originalFilename);
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
