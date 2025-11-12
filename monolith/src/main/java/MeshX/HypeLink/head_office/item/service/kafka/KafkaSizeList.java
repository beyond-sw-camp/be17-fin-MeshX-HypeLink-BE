package MeshX.HypeLink.head_office.item.service.kafka;

import MeshX.HypeLink.head_office.item.model.entity.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class KafkaSizeList {
    private List<KafkaSize> sizes;

    public static KafkaSizeList toDto(List<Size> categories) {
        return KafkaSizeList.builder()
                .sizes(categories.stream()
                        .map(KafkaSize::toDto)
                        .toList())
                .build();
    }
}
