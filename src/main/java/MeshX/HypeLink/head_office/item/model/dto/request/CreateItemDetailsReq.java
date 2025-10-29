package MeshX.HypeLink.head_office.item.model.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateItemDetailsReq {
    private Integer itemId;
    private List<CreateItemDetailReq> details;
}
