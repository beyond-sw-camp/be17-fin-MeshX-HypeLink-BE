package MeshX.HypeLink.direct_store.item.clothes.bottom.model.dto.request;


import MeshX.HypeLink.direct_store.item.clothes.bottom.model.entity.BottomClothesCategory;
import MeshX.HypeLink.direct_store.item.clothes.bottom.model.entity.DirectBottomClothes;
import lombok.Getter;

@Getter
public class DirectBottomCreateReq {
    private BottomClothesCategory category;
    private Integer amount;
    private String name;
    private String content;
    private String company;
    private String itemCode;
    private Integer stock;
    private Integer waist;          // 허리 사이즈
    private Integer length;         // 바지 길이
    private String size;
    private String gender;
    private String season;

    public DirectBottomClothes toEntity() {
        return DirectBottomClothes.builder()
                .category(category)
                .amount(amount)
                .name(name)
                .content(content)
                .company(company)
                .itemCode(itemCode)
                .stock(stock)
                .waist(waist)
                .length(length)
                .size(size)
                .gender(gender)
                .season(season)
                .build();
    }
}
