package MeshX.HypeLink.direct_store.item.model.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class SaveStoreItemListReq {
    private Integer storeId;
    private List<SaveStoreItemReq> items;
}
