package com.example.apidirect.item.adapter.out.persistence;

import com.example.apidirect.item.adapter.out.entity.StoreItemDetailEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreItemDetailBatchRepository extends AbstractBatchSaveRepository<StoreItemDetailEntity, String> {

    private final StoreItemDetailRepository repository;

    @Override
    protected String extractKey(StoreItemDetailEntity entity) {
        return entity.getCompositeKey();
    }

    @Override
    protected List<String> findExistingKeys(List<String> keys) {
        return repository.findExistingItemDetailCodes(keys);
    }

    @Override
    protected void saveAllToDb(List<StoreItemDetailEntity> entities) {
        repository.saveAll(entities);
    }

    @Override
    protected String getEntityName() {
        return "StoreItemDetail";
    }

    public void upsert(StoreItemDetailEntity entity) {
        repository.upsert(entity);
    }
}
