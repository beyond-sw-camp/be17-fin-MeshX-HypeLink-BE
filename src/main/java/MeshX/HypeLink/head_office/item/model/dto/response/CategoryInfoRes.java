package MeshX.HypeLink.head_office.item.model.dto.response;

import MeshX.HypeLink.head_office.item.model.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryInfoRes {
    private String category;

    public static CategoryInfoRes toDto(Category entity) {
        return CategoryInfoRes.builder()
                .category(entity.getCategory())
                .build();
    }
}
