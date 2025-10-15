package MeshX.HypeLink.direct_store.item.clothes.top.model.dto.request;



import MeshX.HypeLink.direct_store.item.clothes.top.model.entity.DirectTopClothes;
import MeshX.HypeLink.direct_store.item.clothes.top.model.entity.TopClothesCategory;
import lombok.Getter;

@Getter
public class DirectTopCreateReq {
    private TopClothesCategory category;
    private Integer amount;
    private String name;
    private String content;
    private String company;
    private String itemCode;
    private Integer stock;
    private Boolean longSleeve;     // 긴팔 여부
    private String neckline;        // 라운드넥, 브이넥 등
    private String size;
    private String gender;
    private String season;

    public DirectTopClothes toEntity() {
        return DirectTopClothes.builder()
                .category(category)
                .amount(amount)
                .name(name)
                .content(content)
                .company(company)
                .itemCode(itemCode)
                .stock(stock)
                .longSleeve(longSleeve)
                .neckline(neckline)
                .size(size)
                .gender(gender)
                .season(season)
                .build();
    }
}
