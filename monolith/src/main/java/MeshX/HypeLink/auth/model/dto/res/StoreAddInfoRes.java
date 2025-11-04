package MeshX.HypeLink.auth.model.dto.res;

import MeshX.HypeLink.auth.model.entity.Store;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreAddInfoRes {
    private Integer id;
    private String storeName;
    private Double lat;
    private Double lon;

    public static StoreAddInfoRes toDto(Store store) {
        return StoreAddInfoRes.builder()
                .id(store.getId())
                .lat(store.getLat())
                .lon(store.getLon())
                .storeName(store.getMember().getName())
                .build();
    }
}
