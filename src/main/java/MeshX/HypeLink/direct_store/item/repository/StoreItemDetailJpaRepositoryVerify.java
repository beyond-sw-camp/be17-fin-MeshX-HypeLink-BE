package MeshX.HypeLink.direct_store.item.repository;

import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StoreItemDetailJpaRepositoryVerify extends AbstractBatchSaveRepository<StoreItemDetail, String> {
    private final StoreItemDetailRepository repository;

    @Override
    protected String extractKey(StoreItemDetail entity) {
        return entity.getCompositeKey();
    }

    @Override
    protected List<String> findExistingKeys(List<String> keys) {
        return repository.findAllByCompositeKeyIn(keys)
                .stream()
                .map(StoreItemDetail::getCompositeKey)
                .toList();
    }

    @Override
    protected void saveAllToDb(List<StoreItemDetail> entities) {
        repository.saveAll(entities);
    }

    public StoreItemDetail findById(Integer id) {
        Optional<StoreItemDetail> optional = repository.findById(id);

        if(optional.isPresent()) {
            return optional.get();
        }

        throw new BaseException(null);
    }
}
