package MeshX.HypeLink.head_office.item.model.dto.request;

import lombok.Getter;

@Getter
public class UpdateItemStockReq {
    private String itemDetailCode;
    private Integer stock;
}
