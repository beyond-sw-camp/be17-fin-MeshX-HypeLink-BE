package MeshX.HypeLink.head_office.item.clothes.outer.model.dto.response;



import MeshX.HypeLink.head_office.item.clothes.outer.model.entity.OuterClothes;
import MeshX.HypeLink.head_office.item.clothes.outer.model.entity.OuterClothesCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HeadOuterInfoRes {
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

    public static HeadOuterInfoRes toDto(OuterClothes entity) {
        return HeadOuterInfoRes.builder()
                .category(entity.getCategory())
                .amount(entity.getAmount())
                .name(entity.getName())
                .content(entity.getContent())
                .company(entity.getCompany())
                .itemCode(entity.getItemCode())
                .stock(entity.getStock())
                .hooded(entity.getHooded())
                .waterproof(entity.getWaterproof())
                .size(entity.getSize())
                .gender(entity.getGender())
                .season(entity.getSeason())
                .build();
    }

    @Builder
    private HeadOuterInfoRes(OuterClothesCategory category, Integer amount, String name, String content, String company, String itemCode, Integer stock, Boolean hooded, Boolean waterproof, String size, String gender, String season) {
        this.category = category;
        this.amount = amount;
        this.name = name;
        this.content = content;
        this.company = company;
        this.itemCode = itemCode;
        this.stock = stock;
        this.hooded = hooded;
        this.waterproof = waterproof;
        this.gender = gender;
        this.season = season;
        this.size= size;
    }
}
