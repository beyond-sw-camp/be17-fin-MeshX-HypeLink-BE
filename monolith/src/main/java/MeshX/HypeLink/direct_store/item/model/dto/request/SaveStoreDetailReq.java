package MeshX.HypeLink.direct_store.item.model.dto.request;

import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail;
import lombok.Getter;

@Getter
public class SaveStoreDetailReq {
    private String size;
    private String color;
    private String colorCode;
    private Integer stock;
    private String itemDetailCode;

    public StoreItemDetail toEntity(StoreItem item) {
        return StoreItemDetail.builder()
                .size(size)
                .itemDetailCode(itemDetailCode)
                .colorCode(colorCode)
                .color(color)
                .stock(stock)
                .item(item)
                .build();
    }
}
