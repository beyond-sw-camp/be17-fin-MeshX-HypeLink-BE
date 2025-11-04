package MeshX.HypeLink.direct_store.item.model.dto.response;

import MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreItemDetailInfoRes {
    private Integer id;
    private Integer stock;

    public static StoreItemDetailInfoRes toDto(StoreItemDetail itemDetail) {
        return StoreItemDetailInfoRes.builder()
                .id(itemDetail.getId())
                .stock(itemDetail.getStock())
                .build();
    }
}
