package MeshX.HypeLink.direct_store.item.model.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ItemInfoListRes {
    List<ItemInfoRes> itemInfoList;

    @Builder
    private ItemInfoListRes(List<ItemInfoRes> itemInfoList) {
        this.itemInfoList = itemInfoList;
    }
}
