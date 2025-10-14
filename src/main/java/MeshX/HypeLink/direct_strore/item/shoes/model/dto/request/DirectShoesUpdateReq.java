package MeshX.HypeLink.direct_strore.item.shoes.model.dto.request;


import MeshX.HypeLink.direct_strore.item.shoes.model.entity.ShoesCategory;
import lombok.Getter;

@Getter
public class DirectShoesUpdateReq {
    private ShoesCategory category;
    private Integer amount;
    private String name;
    private String content;
    private String company;
    private String itemCode;
    private Integer stock;
    private Integer size;
    private Boolean waterproof;
}
