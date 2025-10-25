package MeshX.HypeLink.head_office.item.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SaveStoreCategoriesReq {
    private Integer storeId;
    private List<SaveStoreCategoryReq> categories;
}
