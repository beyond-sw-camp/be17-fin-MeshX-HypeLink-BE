package MeshX.HypeLink.head_office.item.clothes.top.model.dto.response;



import MeshX.HypeLink.head_office.item.clothes.top.model.entity.TopClothes;
import MeshX.HypeLink.head_office.item.clothes.top.model.entity.TopClothesCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HeadTopInfoRes {
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

    public static HeadTopInfoRes toDto(TopClothes entity) {
        return HeadTopInfoRes.builder()
                .category(entity.getCategory())
                .amount(entity.getAmount())
                .name(entity.getName())
                .content(entity.getContent())
                .company(entity.getCompany())
                .itemCode(entity.getItemCode())
                .stock(entity.getStock())
                .longSleeve(entity.getLongSleeve())
                .neckline(entity.getNeckline())
                .size(entity.getSize())
                .gender(entity.getGender())
                .season(entity.getSeason())
                .build();
    }

    @Builder
    private HeadTopInfoRes(TopClothesCategory category, Integer amount, String name, String content, String company, String itemCode, Integer stock, Boolean longSleeve, String neckline, String size, String gender, String season) {
        this.category = category;
        this.amount = amount;
        this.name = name;
        this.content = content;
        this.company = company;
        this.itemCode = itemCode;
        this.stock = stock;
        this.longSleeve = longSleeve;
        this.neckline = neckline;
        this.gender = gender;
        this.season = season;
        this.size= size;
    }
}
