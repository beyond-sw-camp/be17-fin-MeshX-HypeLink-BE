package MeshX.HypeLink.head_office.item.service.kafka;

import MeshX.HypeLink.head_office.item.model.entity.Category;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class KafkaCategoryList {
    private List<KafkaCategory> categories;

    public static KafkaCategoryList toDto(List<Category> categories) {
        return KafkaCategoryList.builder()
                .categories(categories.stream()
                        .map(KafkaCategory::toDto)
                        .toList())
                .build();
    }
}
