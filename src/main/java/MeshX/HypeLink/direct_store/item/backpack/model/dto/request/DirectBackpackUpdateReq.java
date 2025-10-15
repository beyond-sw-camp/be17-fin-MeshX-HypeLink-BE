package MeshX.HypeLink.direct_store.item.backpack.model.dto.request;

import MeshX.HypeLink.direct_store.item.backpack.model.entity.BackPackCategory;
import lombok.Getter;

@Getter
public class DirectBackpackUpdateReq {
    private BackPackCategory category;
    private Integer amount;
    private String name;
    private String content;
    private String company;
    private String itemCode;
    private Integer stock;
    private Integer capacity;
    private Boolean waterproof;
}
