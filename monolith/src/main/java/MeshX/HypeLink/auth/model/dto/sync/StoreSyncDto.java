package MeshX.HypeLink.auth.model.dto.sync;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.auth.model.entity.StoreState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreSyncDto {
    private Integer id;
    private Double lat;
    private Double lon;
    private Integer posCount;
    private String storeNumber;
    private StoreState storeState;
    private Integer memberId;

    public static StoreSyncDto from(Store store) {
        return StoreSyncDto.builder()
                .id(store.getId())
                .lat(store.getLat())
                .lon(store.getLon())
                .posCount(store.getPosCount())
                .storeNumber(store.getStoreNumber())
                .storeState(store.getStoreState())
                .memberId(store.getMember() != null ? store.getMember().getId() : null)
                .build();
    }
}
