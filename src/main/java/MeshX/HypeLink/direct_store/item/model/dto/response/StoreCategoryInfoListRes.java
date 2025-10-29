package MeshX.HypeLink.direct_store.item.model.dto.response;

import MeshX.HypeLink.direct_store.item.model.entity.StoreCategory;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StoreCategoryInfoListRes {
    private List<StoreCategoryInfoRes> storeCategoryInfoResList;

    public static StoreCategoryInfoListRes toDto(List<StoreCategory> entities) {
        return StoreCategoryInfoListRes.builder()
                .storeCategoryInfoResList(entities.stream().map(StoreCategoryInfoRes::toDto).toList())
                .build();
    }
}
