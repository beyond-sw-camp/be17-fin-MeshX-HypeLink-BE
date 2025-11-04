package MeshX.HypeLink.head_office.item.repository;

import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.head_office.item.model.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.item.exception.CategoryExceptionMessage.NOTFOUND_ID;
import static MeshX.HypeLink.head_office.item.exception.CategoryExceptionMessage.NOTFOUND_NAME;

@Repository
@RequiredArgsConstructor
public class CategoryJpaRepositoryVerify {
    private final CategoryRepository repository;

    public void save(Category entity) {
        repository.save(entity);
    }

    public Category isExist(String name) {
        Optional<Category> optional = repository.findByCategory(name);

        if (optional.isEmpty()) {
            throw new BaseException(NOTFOUND_NAME);
        }

        return optional.get();
    }

    public Category findById(Integer id) {
        Optional<Category> optional = repository.findByIdAndIsDeletedIsFalse(id);

        if (optional.isEmpty()) {
            throw new BaseException(NOTFOUND_ID);
        }

        return optional.get();
    }

    public Category findByName(String name) {
        Optional<Category> optional = repository.findByCategoryAndIsDeletedIsFalse(name);

        if (optional.isEmpty()) {
            throw new BaseException(NOTFOUND_NAME);
        }

        return optional.get();
    }

    public List<Category> findAll() {
        List<Category> all = repository.findAll();
        if(all.isEmpty()) {
            throw new BaseException(null);
        }
        return all;
    }
}
