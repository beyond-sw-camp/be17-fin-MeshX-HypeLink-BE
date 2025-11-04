package MeshX.HypeLink.auth.model.dto.res;

import MeshX.HypeLink.auth.model.entity.Store;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StoreAddInfoListRes {
    private List<StoreAddInfoRes> stores;

    public static StoreAddInfoListRes toDto(List<Store> stores) {
        return StoreAddInfoListRes.builder()
                .stores(stores.stream().map(StoreAddInfoRes::toDto).toList())
                .build();
    }
}
