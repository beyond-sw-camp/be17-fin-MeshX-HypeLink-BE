package MeshX.HypeLink.direct_strore.item.shoes.model.dto.response;



import MeshX.HypeLink.direct_strore.item.shoes.model.entity.DirectShoes;
import MeshX.HypeLink.direct_strore.item.shoes.model.entity.ShoesCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DirectShoesInfoRes {
    private ShoesCategory category;
    private Integer amount;
    private String name;
    private String content;
    private String company;
    private String itemCode;
    private Integer stock;
    private Integer size;
    private Boolean waterproof;

    public static DirectShoesInfoRes toDto(DirectShoes entity) {
        return DirectShoesInfoRes.builder()
                .category(entity.getCategory())
                .amount(entity.getAmount())
                .name(entity.getName())
                .content(entity.getContent())
                .company(entity.getCompany())
                .itemCode(entity.getItemCode())
                .stock(entity.getStock())
                .size(entity.getSize())
                .waterproof(entity.getWaterproof())
                .build();
    }

    @Builder
    private DirectShoesInfoRes(ShoesCategory category, Integer amount, String name, String content, String company, String itemCode, Integer stock, Boolean waterproof, Integer size) {
        this.category = category;
        this.amount = amount;
        this.name = name;
        this.content = content;
        this.company = company;
        this.itemCode = itemCode;
        this.stock = stock;
        this.waterproof = waterproof;
        this.size = size;
    }
}
