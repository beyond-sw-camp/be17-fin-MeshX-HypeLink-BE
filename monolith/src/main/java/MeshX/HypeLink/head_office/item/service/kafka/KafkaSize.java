package MeshX.HypeLink.head_office.item.service.kafka;

import MeshX.HypeLink.head_office.item.model.entity.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KafkaSize {
    private Integer id;
    private String size;

    public static KafkaSize toDto(Size size) {
        return KafkaSize.builder()
                .id(size.getId())
                .size(size.getSize())
                .build();
    }
}
