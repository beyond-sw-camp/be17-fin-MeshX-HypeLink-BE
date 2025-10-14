package MeshX.HypeLink.head_office.item.shoes.model.dto.request;



import MeshX.HypeLink.head_office.item.shoes.model.entity.Shoes;
import MeshX.HypeLink.head_office.item.shoes.model.entity.ShoesCategory;
import lombok.Getter;

@Getter
public class HeadShoesCreateReq {
    private ShoesCategory category;
    private Integer amount;
    private String name;
    private String content;
    private String company;
    private String itemCode;
    private Integer stock;
    private Integer size;
    private Boolean waterproof;

    public Shoes toEntity() {
        return Shoes.builder()
                .category(category)
                .amount(amount)
                .name(name)
                .content(content)
                .company(company)
                .itemCode(itemCode)
                .stock(stock)
                .size(size)
                .waterproof(waterproof)
                .build();
    }
}
