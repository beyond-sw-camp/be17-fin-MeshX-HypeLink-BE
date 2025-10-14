package MeshX.HypeLink.head_office.item.backpack.model.dto.request;



import MeshX.HypeLink.head_office.item.backpack.model.entity.BackPack;
import MeshX.HypeLink.head_office.item.backpack.model.entity.BackPackCategory;
import lombok.Getter;

@Getter
public class HeadBackpackCreateReq {
    private BackPackCategory category;
    private Integer amount;
    private String name;
    private String content;
    private String company;
    private String itemCode;
    private Integer stock;
    private Integer capacity;
    private Boolean waterproof;

    public BackPack toEntity() {
        return BackPack.builder()
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
