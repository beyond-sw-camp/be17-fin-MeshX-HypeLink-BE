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
        // ✅ storeId + itemDetailCode 조합으로 중복 체크
        // 같은 itemDetailCode라도 store가 다르면 별도 저장 (각 store마다 독립적인 재고 관리)
        return entity.getCompositeKey();  // "storeId-itemDetailCode"
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
}
