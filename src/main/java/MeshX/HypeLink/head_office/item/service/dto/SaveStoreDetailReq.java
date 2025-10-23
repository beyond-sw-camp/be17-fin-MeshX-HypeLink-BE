package MeshX.HypeLink.head_office.item.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveStoreDetailReq {
    private String size;
    private String color;
    private String colorCode;
    private Integer stock;
    private String itemDetailCode;
}
