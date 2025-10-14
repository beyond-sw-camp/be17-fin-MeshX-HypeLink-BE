package MeshX.HypeLink.head_office.item.clothes.outer.model.dto.request;



import MeshX.HypeLink.head_office.item.clothes.outer.model.entity.OuterClothes;
import MeshX.HypeLink.head_office.item.clothes.outer.model.entity.OuterClothesCategory;
import lombok.Getter;

@Getter
public class HeadOuterCreateReq {
    private OuterClothesCategory category;
    private Integer amount;
    private String name;
    private String content;
    private String company;
    private String itemCode;
    private Integer stock;
    private Boolean hooded;         // 후드 여부
    private Boolean waterproof;     // 방수 기능 여부
    private String size;
    private String gender;
    private String season;

    public OuterClothes toEntity() {
        return OuterClothes.builder()
                .category(category)
                .amount(amount)
                .name(name)
                .content(content)
                .company(company)
                .itemCode(itemCode)
                .stock(stock)
                .hooded(hooded)
                .waterproof(waterproof)
                .size(size)
                .gender(gender)
                .season(season)
                .build();
    }
}
