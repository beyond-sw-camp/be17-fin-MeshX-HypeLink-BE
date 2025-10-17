package MeshX.HypeLink.head_office.item.model.dto.request;

import lombok.Getter;

@Getter
public class UpdateItemAmountReq {
    private String itemDetailCode;
    private Integer amount;
}
