package MeshX.HypeLink.direct_strore.item.backpack.model.dto.request;

import MeshX.HypeLink.direct_strore.item.backpack.model.entity.BackPackCategory;
import MeshX.HypeLink.direct_strore.item.backpack.model.entity.DirectBackPack;
import lombok.Getter;

@Getter
public class DirectBackpackCreateReq {
    private BackPackCategory category;
    private Integer amount;
    private String name;
    private String content;
    private String company;
    private String itemCode;
    private Integer stock;
    private Integer capacity;
    private Boolean waterproof;

    public DirectBackPack toEntity() {
        return DirectBackPack.builder()
                .category(category)
                .amount(amount)
                .name(name)
                .content(content)
                .company(company)
                .itemCode(itemCode)
                .stock(stock)
                .capacity(capacity)
                .waterproof(waterproof)
                .build();
    }
}
