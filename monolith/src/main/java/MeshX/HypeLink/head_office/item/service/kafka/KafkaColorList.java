package MeshX.HypeLink.head_office.item.service.kafka;

import MeshX.HypeLink.head_office.item.model.entity.Color;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class KafkaColorList {
    private List<KafkaColor> colors;

    public static KafkaColorList toDto(List<Color> categories) {
        return KafkaColorList.builder()
                .colors(categories.stream()
                        .map(KafkaColor::toDto)
                        .toList())
                .build();
    }
}
