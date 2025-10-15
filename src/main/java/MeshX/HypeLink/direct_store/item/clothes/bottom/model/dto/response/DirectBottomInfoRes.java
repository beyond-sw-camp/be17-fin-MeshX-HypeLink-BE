package MeshX.HypeLink.direct_store.item.clothes.bottom.model.dto.response;



import MeshX.HypeLink.direct_store.item.clothes.bottom.model.entity.BottomClothesCategory;
import MeshX.HypeLink.direct_store.item.clothes.bottom.model.entity.DirectBottomClothes;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DirectBottomInfoRes {
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

    public static DirectBottomInfoRes toDto(DirectBottomClothes entity) {
        return DirectBottomInfoRes.builder()
                .category(entity.getCategory())
                .amount(entity.getAmount())
                .name(entity.getName())
                .content(entity.getContent())
                .company(entity.getCompany())
                .itemCode(entity.getItemCode())
                .stock(entity.getStock())
                .waist(entity.getWaist())
                .length(entity.getLength())
                .size(entity.getSize())
                .gender(entity.getGender())
                .season(entity.getSeason())
                .build();
    }

    @Builder
    private DirectBottomInfoRes(BottomClothesCategory category, Integer amount, String name, String content, String company, String itemCode, Integer stock, Integer waist, Integer length, String size, String gender, String season) {
        this.category = category;
        this.amount = amount;
        this.name = name;
        this.content = content;
        this.company = company;
        this.itemCode = itemCode;
        this.stock = stock;
        this.waist = waist;
        this.length = length;
        this.gender = gender;
        this.season = season;
        this.size= size;
    }
}
