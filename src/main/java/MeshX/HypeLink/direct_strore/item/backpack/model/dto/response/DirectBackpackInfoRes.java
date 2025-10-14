package MeshX.HypeLink.direct_strore.item.backpack.model.dto.response;


import MeshX.HypeLink.direct_strore.item.backpack.model.entity.BackPackCategory;
import MeshX.HypeLink.direct_strore.item.backpack.model.entity.DirectBackPack;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class DirectBackpackInfoRes {
    private BackPackCategory category;
    private Integer amount;
    private String name;
    private String content;
    private String company;
    private String itemCode;
    private Integer stock;
    private Integer capacity;
    private Boolean waterproof;

    public static DirectBackpackInfoRes toDto(DirectBackPack entity) {
        return DirectBackpackInfoRes.builder()
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
    private DirectBackpackInfoRes(BackPackCategory category, Integer amount, String name, String content, String company, String itemCode, Integer stock, Boolean waterproof, Integer capacity) {
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

    public static Page<DirectBackpackInfoRes> toDtoPage(Page<DirectBackPack>  page) {
        return page.map(DirectBackpackInfoRes::toDto);
    }
}
