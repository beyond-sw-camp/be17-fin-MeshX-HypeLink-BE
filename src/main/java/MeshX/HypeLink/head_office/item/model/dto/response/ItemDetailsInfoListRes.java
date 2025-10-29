package MeshX.HypeLink.head_office.item.model.dto.response;

import MeshX.HypeLink.head_office.item.model.entity.ItemDetail;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ItemDetailsInfoListRes {
    private List<ItemDetailInfoRes> itemInfoResList;

    public static ItemDetailsInfoListRes toDto(List<ItemDetail> itemDetails) {
        return ItemDetailsInfoListRes.builder()
                .itemInfoResList(itemDetails.stream().map(ItemDetailInfoRes::toDto).toList())
                .build();
    }
}
