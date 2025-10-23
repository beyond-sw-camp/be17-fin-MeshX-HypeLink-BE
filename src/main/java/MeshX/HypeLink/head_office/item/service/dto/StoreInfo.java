package MeshX.HypeLink.head_office.item.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreInfo {
    private Integer id;
    private String name;
    private String apiUrl;
}
