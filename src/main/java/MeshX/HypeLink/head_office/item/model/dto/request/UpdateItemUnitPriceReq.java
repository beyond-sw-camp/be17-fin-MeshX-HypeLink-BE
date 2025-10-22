package MeshX.HypeLink.head_office.item.model.dto.request;

import lombok.Getter;

@Getter
public class UpdateItemUnitPriceReq {
    private String itemCode;
    private Integer unitPrice;
}
