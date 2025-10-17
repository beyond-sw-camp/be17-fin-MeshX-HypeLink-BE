package MeshX.HypeLink.head_office.item.model.dto.response;

import MeshX.HypeLink.head_office.item.model.entity.Item;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ItemSearchListRes {
    List<ItemSearchRes> itemInfoList;

    @Builder
    private ItemSearchListRes(List<ItemSearchRes> itemInfoList) {
        this.itemInfoList = itemInfoList;
    }

    public static ItemSearchListRes toDto(List<Item> items) {
       return ItemSearchListRes.builder()
               .itemInfoList(items.stream().map(ItemSearchRes::toDto).toList())
               .build();
    }
}
