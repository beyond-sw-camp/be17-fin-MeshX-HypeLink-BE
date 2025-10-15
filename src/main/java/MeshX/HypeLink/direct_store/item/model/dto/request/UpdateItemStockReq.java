package MeshX.HypeLink.direct_store.item.model.dto.request;

import lombok.Getter;

@Getter
public class UpdateItemStockReq {
    private String itemCode;
    private Integer stock;
}
