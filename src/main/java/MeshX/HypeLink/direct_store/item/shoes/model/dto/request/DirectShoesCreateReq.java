package MeshX.HypeLink.direct_store.item.shoes.model.dto.request;


import MeshX.HypeLink.direct_store.item.shoes.model.entity.DirectShoes;
import MeshX.HypeLink.direct_store.item.shoes.model.entity.ShoesCategory;
import lombok.Getter;

@Getter
public class DirectShoesCreateReq {
    private ShoesCategory category;
    private Integer amount;
    private String name;
    private String content;
    private String company;
    private String itemCode;
    private Integer stock;
    private Integer size;
    private Boolean waterproof;

    public DirectShoes toEntity() {
        return DirectShoes.builder()
                .category(category)
                .amount(amount)
                .name(name)
                .content(content)
                .company(company)
                .itemCode(itemCode)
                .stock(stock)
                .size(size)
                .waterproof(waterproof)
                .build();
    }
}
