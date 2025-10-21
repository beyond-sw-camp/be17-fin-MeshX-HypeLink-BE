package MeshX.HypeLink.head_office.item.model.dto.request;

import lombok.Getter;

@Getter
public class UpdateItemAmountReq {
    private String itemCode;
    private Integer amount;
}
