package MeshX.HypeLink.direct_store.item.repository;

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
        // 중복 기준: item_id + savedPath
        return entity.getItem().getId() + ":" + entity.getSavedPath();
    }

    @Override
    protected List<String> findExistingKeys(List<String> keys) {
        // item별 쿼리를 단일화하려면 모든 itemId/savedPath 쌍 조회
        // 여기서는 savedPath 기준 예시
        List<String> savedPaths = keys.stream()
                .map(k -> k.split(":", 2)[1]) // "itemId:savedPath" → savedPath만 추출
                .toList();

        // 단일 item 단위로 처리 시 이 방식으로 충분함
        // 여러 itemId 섞인 경우, item별 loop 처리 필요
        return repository.findAllBySavedPathIn(savedPaths)
                .stream()
                .map(img -> img.getItem().getId() + ":" + img.getSavedPath())
                .toList();
    }

    @Override
    protected void saveAllToDb(List<StoreItemImage> entities) {
        repository.saveAll(entities);
    }
}
