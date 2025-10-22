package MeshX.HypeLink.head_office.item.model.dto.response;

import MeshX.HypeLink.head_office.item.model.entity.Item;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ItemInfoListRes {
    private List<ItemInfoRes> itemInfoResList;

    public static ItemInfoListRes toDto(List<Item> items) {
        return ItemInfoListRes.builder()
                .itemInfoResList(items.stream().map(ItemInfoRes::toDto).toList())
                .build();
    }
}
