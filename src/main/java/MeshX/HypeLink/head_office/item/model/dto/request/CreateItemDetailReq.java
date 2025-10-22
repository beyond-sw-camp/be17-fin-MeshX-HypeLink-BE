package MeshX.HypeLink.head_office.item.model.dto.request;

import MeshX.HypeLink.head_office.item.model.entity.*;
import lombok.Getter;

@Getter
public class CreateItemDetailReq {
    private String size;
    private String color;
    private Integer stock; // 재고
    private String itemDetailCode; // 아이템 코드

    public ItemDetail toEntity(Size findSize, Color findColor, Item item) {
        return ItemDetail.builder()
                .itemDetailCode(itemDetailCode)
                .size(findSize)
                .color(findColor)
                .item(item)
                .stock(stock)
                .build();
    }
}
