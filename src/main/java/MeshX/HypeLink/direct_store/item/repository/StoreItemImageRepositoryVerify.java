package MeshX.HypeLink.direct_store.item.repository;

import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItemImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreItemImageRepositoryVerify extends AbstractBatchSaveRepository<StoreItemImage, String> {
    private final StoreItemImageRepository repository;

    @Override
    protected String extractKey(StoreItemImage entity) {
        // 중복 기준: StoreId + savedPath
        return entity.getCompositeKey();
    }

    @Override
    protected List<String> findExistingKeys(List<String> keys) {
        return repository.findAllByCompositeKeyIn(keys)
                .stream()
                .map(StoreItemImage::getCompositeKey)
                .toList();
    }

    @Override
    protected void saveAllToDb(List<StoreItemImage> entities) {
        repository.saveAll(entities);
    }

    public void save(StoreItemImage entity) {
        repository.save(entity);
    }

    public List<StoreItemImage> findByItem(StoreItem item) {
        return repository.findByItem(item);
    }

    public void delete(StoreItemImage entity) {
        repository.delete(entity);
    }
}
