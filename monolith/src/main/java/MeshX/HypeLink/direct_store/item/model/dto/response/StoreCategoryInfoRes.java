package MeshX.HypeLink.direct_store.item.model.dto.response;

import MeshX.HypeLink.direct_store.item.model.entity.StoreCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreCategoryInfoRes {
    private String category;

    public static StoreCategoryInfoRes toDto(StoreCategory entity) {
        return StoreCategoryInfoRes.builder()
                .category(entity.getCategory())
                .build();
    }
}
