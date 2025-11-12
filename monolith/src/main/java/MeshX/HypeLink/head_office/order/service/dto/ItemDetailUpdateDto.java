package MeshX.HypeLink.head_office.order.service.dto;

import MeshX.HypeLink.head_office.item.model.entity.ItemDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemDetailUpdateDto {
    private Integer id;
    private Integer stock;

    public static ItemDetailUpdateDto toDtoPlus(ItemDetail itemDetail, Integer quantity) {
        return ItemDetailUpdateDto.builder()
                .id(itemDetail.getId())
                .stock(quantity)
                .build();
    }

    public static ItemDetailUpdateDto toDtoMinus(ItemDetail itemDetail, Integer quantity) {
        return ItemDetailUpdateDto.builder()
                .id(itemDetail.getId())
                .stock(-1 * quantity)
                .build();
    }
}
