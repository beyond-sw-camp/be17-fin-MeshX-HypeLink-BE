package MeshX.HypeLink.head_office.item.model.dto.response;

import MeshX.HypeLink.head_office.item.model.entity.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemStockRes {
    private Integer id;
    private String color;
    private String size;
    private Integer stock;

    public static ItemStockRes toDto(Item item) {
        return ItemStockRes.builder()
                .id(item.getId())
                .color(item.getColor().getColorCode())
                .size(item.getSize().getSize())
                .stock(item.getStock())
                .build();
    }
}
