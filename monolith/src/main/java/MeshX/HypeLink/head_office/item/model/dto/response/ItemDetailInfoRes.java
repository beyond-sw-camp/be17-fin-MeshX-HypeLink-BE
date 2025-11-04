package MeshX.HypeLink.head_office.item.model.dto.response;

import MeshX.HypeLink.head_office.item.model.entity.ItemDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemDetailInfoRes {
    private Integer id;
    private String itemDetailCode;
    private String color;
    private String colorCode;
    private String size;
    private Integer stock;

    public static ItemDetailInfoRes toDto(ItemDetail itemDetail) {
        return ItemDetailInfoRes.builder()
                .id(itemDetail.getId())
                .itemDetailCode(itemDetail.getItemDetailCode())
                .color(itemDetail.getColor().getColorName())
                .colorCode(itemDetail.getColor().getColorCode())
                .size(itemDetail.getSize().getSize())
                .stock(itemDetail.getStock())
                .build();
    }
}
