package MeshX.HypeLink.head_office.item.model.dto.response;

import MeshX.HypeLink.head_office.item.model.entity.Category;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CategoryInfoListRes {
    private List<CategoryInfoRes> categories;

    public static CategoryInfoListRes toDto(List<Category> entities) {
        return CategoryInfoListRes.builder()
                .categories(entities.stream().map(CategoryInfoRes::toDto).toList())
                .build();
    }
}
