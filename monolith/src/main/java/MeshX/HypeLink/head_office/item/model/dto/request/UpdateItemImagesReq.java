package MeshX.HypeLink.head_office.item.model.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateItemImagesReq {
    private Integer itemId;
    private List<CreateItemImageReq> images;
}
