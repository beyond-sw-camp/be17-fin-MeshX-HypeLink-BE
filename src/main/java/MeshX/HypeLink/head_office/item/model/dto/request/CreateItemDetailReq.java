package MeshX.HypeLink.head_office.item.model.dto.request;

import MeshX.HypeLink.head_office.item.model.entity.Category;
import MeshX.HypeLink.head_office.item.model.entity.Color;
import MeshX.HypeLink.head_office.item.model.entity.Item;
import MeshX.HypeLink.head_office.item.model.entity.Size;
import lombok.Getter;

@Getter
public class CreateItemDetailReq {
    private String size;
    private String color;
    private Integer stock; // 재고
    private String itemDetailCode; // 아이템 코드

    public Item toEntity(Integer amount, String enName, String koName, String company,
                         String itemCode, String content, Category findCategory, Color findColor,
                         Size findSize) {
        return Item.builder()
                .itemCode(itemCode)
                .itemDetailCode(itemDetailCode)
                .color(findColor)
                .size(findSize)
                .stock(stock)
                .amount(amount)
                .category(findCategory)
                .company(company)
                .enName(enName)
                .koName(koName)
                .content(content)
                .build();
    }
}
