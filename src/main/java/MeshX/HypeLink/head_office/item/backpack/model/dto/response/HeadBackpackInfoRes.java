package MeshX.HypeLink.head_office.item.backpack.model.dto.response;



import MeshX.HypeLink.head_office.item.backpack.model.entity.BackPack;
import MeshX.HypeLink.head_office.item.backpack.model.entity.BackPackCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HeadBackpackInfoRes {
    private BackPackCategory category;
    private Integer amount;
    private String name;
    private String content;
    private String company;
    private String itemCode;
    private Integer stock;
    private Integer capacity;
    private Boolean waterproof;

    public static HeadBackpackInfoRes toDto(BackPack entity) {
        return HeadBackpackInfoRes.builder()
                .category(entity.getCategory())
                .amount(entity.getAmount())
                .name(entity.getName())
                .content(entity.getContent())
                .company(entity.getCompany())
                .itemCode(entity.getItemCode())
                .stock(entity.getStock())
                .capacity(entity.getCapacity())
                .waterproof(entity.getWaterproof())
                .build();
    }

    @Builder
    private HeadBackpackInfoRes(BackPackCategory category, Integer amount, String name, String content, String company, String itemCode, Integer stock, Boolean waterproof, Integer capacity) {
        this.category = category;
        this.amount = amount;
        this.name = name;
        this.content = content;
        this.company = company;
        this.itemCode = itemCode;
        this.stock = stock;
        this.waterproof = waterproof;
        this.capacity = capacity;
    }
}
