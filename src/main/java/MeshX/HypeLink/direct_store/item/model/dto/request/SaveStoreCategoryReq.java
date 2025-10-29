package MeshX.HypeLink.direct_store.item.model.dto.request;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.direct_store.item.model.entity.StoreCategory;
import lombok.Getter;

@Getter
public class SaveStoreCategoryReq {
    private String category;

    public StoreCategory toEntity(Store store) {
        return StoreCategory.builder()
                .category(category)
                .store(store)
                .build();
    }
}
