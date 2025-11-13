package MeshX.HypeLink.head_office.item.service.kafka;

import MeshX.HypeLink.head_office.item.model.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KafkaCategory {
    private Integer id;
    private String category;

    public static KafkaCategory toDto(Category category) {
        return KafkaCategory.builder()
                .id(category.getId())
                .category(category.getCategory())
                .build();
    }
}
