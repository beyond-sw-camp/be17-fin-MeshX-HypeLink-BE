package MeshX.HypeLink.direct_store.item.model.dto.request;

import MeshX.HypeLink.direct_store.item.model.entity.StoreCategory;
import lombok.Getter;

import java.util.List;

@Getter
public class SaveStoreCategoriesReq {
    private List<SaveStoreCategoryReq> categories;

    public List<StoreCategory> toEntity() {
        return categories.stream().map(SaveStoreCategoryReq::toEntity).toList();
    }
}
