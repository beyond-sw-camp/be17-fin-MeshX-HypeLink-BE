package MeshX.HypeLink.head_office.item.model.dto.request;

import MeshX.HypeLink.head_office.item.model.entity.Category;
import MeshX.HypeLink.head_office.item.model.entity.Item;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateItemReq {
    private String enName;      // 이름
    private String koName;      // 이름
    private Integer amount;     // 가격
    private Integer unitPrice;  // 원가
    private String category;
    private String itemCode;
    private String content;     // 아이템 설명
    private String company;     // 회사
    private List<CreateItemDetailReq> itemDetailList;
    private List<CreateItemImageReq> itemImages;

    public Item toEntity(Category findCategory) {
        return Item.builder()
                .enName(enName)
                .koName(koName)
                .amount(amount)
                .unitPrice(unitPrice)
                .category(findCategory)
                .itemCode(itemCode)
                .content(content)
                .company(company)
                .build();
    }
}
