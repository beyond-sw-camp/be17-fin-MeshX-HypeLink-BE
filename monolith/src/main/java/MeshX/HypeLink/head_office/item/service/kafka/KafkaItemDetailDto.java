package MeshX.HypeLink.head_office.item.service.kafka;

import MeshX.HypeLink.head_office.item.model.entity.ItemDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KafkaItemDetailDto {
    private Integer id;
    private Integer colorId;
    private Integer sizeId;
    private String itemDetailCode;
    private Integer stock;

    public static KafkaItemDetailDto toDto(ItemDetail entity) {
        return KafkaItemDetailDto.builder()
                .id(entity.getId())
                .colorId(entity.getColor().getId())
                .sizeId(entity.getSize().getId())
                .itemDetailCode(entity.getItemDetailCode())
                .stock(entity.getStock())
                .build();
    }
}
