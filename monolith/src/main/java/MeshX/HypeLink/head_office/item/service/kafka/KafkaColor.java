package MeshX.HypeLink.head_office.item.service.kafka;

import MeshX.HypeLink.head_office.item.model.entity.Color;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KafkaColor {
    private Integer id;
    private String colorName;
    private String colorCode;

    public static KafkaColor toDto(Color color) {
        return KafkaColor.builder()
                .id(color.getId())
                .colorName(color.getColorName())
                .colorCode(color.getColorCode())
                .build();
    }
}
