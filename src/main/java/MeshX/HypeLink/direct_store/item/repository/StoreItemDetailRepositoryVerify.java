package MeshX.HypeLink.direct_store.item.repository;

import MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreItemDetailRepositoryVerify extends AbstractBatchSaveRepository<StoreItemDetail, String> {
    private final StoreItemDetailRepository repository;

    @Override
    protected String extractKey(StoreItemDetail entity) {
        return entity.getItemDetailCode();
    }

    @Override
    protected List<String> findExistingKeys(List<String> keys) {
        return repository.findAllByItemDetailCodeIn(keys)
                .stream()
                .map(StoreItemDetail::getItemDetailCode)
                .toList();
    }

    @Override
    protected void saveAllToDb(List<StoreItemDetail> entities) {
        repository.saveAll(entities);
    }
}
