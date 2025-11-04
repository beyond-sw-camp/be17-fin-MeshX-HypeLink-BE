package MeshX.HypeLink.head_office.item.model.dto.response;

import MeshX.HypeLink.head_office.item.model.entity.Item;
import MeshX.HypeLink.image.model.entity.Image;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;

@Getter
@Builder
public class ItemInfoListRes {
    private List<ItemInfoRes> itemInfoResList;

    public static ItemInfoListRes toDto(List<Item> items, Function<Image, String> urlGenerator) {
        return ItemInfoListRes.builder()
                .itemInfoResList(items.stream().map(one -> ItemInfoRes.toDto(one, urlGenerator)).toList())
                .build();
    }
}
