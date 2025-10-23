package MeshX.HypeLink.direct_store.item.repository;

import MeshX.HypeLink.direct_store.item.exception.StoreCategoryException;
import MeshX.HypeLink.direct_store.item.model.entity.StoreCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.direct_store.item.exception.StoreCategoryExceptionMessage.NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class StoreCategoryJpaRepositoryVerify extends AbstractBatchSaveRepository<StoreCategory, String> {
    private final StoreCategoryRepository repository;

    public void save(StoreCategory entity) {
        repository.save(entity);
    }

    public StoreCategory findById(Integer id){
        Optional<StoreCategory> optional = repository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new StoreCategoryException(NOT_FOUND);
    }

    public StoreCategory findByCategory(String category){
        Optional<StoreCategory> optional = repository.findByCategory(category);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new StoreCategoryException(NOT_FOUND);
    }

    public List<StoreCategory> findAll() {
        List<StoreCategory> all = repository.findAll();
        if(all.isEmpty()) {
            throw new StoreCategoryException(NOT_FOUND);
        }
        return all;
    }

    @Override
    protected String extractKey(StoreCategory entity) {
        return entity.getCategory(); // 중복 판별 기준 필드
    }

    @Override
    protected List<String> findExistingKeys(List<String> keys) {
        return repository.findAllByCategoryIn(keys)
                .stream()
                .map(StoreCategory::getCategory)
                .toList();
    }

    @Override
    protected void saveAllToDb(List<StoreCategory> entities) {
        repository.saveAll(entities);
    }
}
