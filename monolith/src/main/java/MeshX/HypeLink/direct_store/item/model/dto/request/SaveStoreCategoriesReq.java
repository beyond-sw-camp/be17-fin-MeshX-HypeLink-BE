package MeshX.HypeLink.direct_store.item.model.dto.request;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.direct_store.item.model.entity.StoreCategory;
import lombok.Getter;

import java.util.List;

@Getter
public class SaveStoreCategoriesReq {
    private Integer storeId;
    private List<SaveStoreCategoryReq> categories;

    public List<StoreCategory> toEntity(Store store) {
        return categories.stream().map(one -> one.toEntity(store)).toList();
    }
}
