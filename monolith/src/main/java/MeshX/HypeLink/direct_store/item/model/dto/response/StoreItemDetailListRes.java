package MeshX.HypeLink.direct_store.item.model.dto.response;

import MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class StoreItemDetailListRes {
    private List<StoreItemDetailRes> items;
    private Integer totalCount;

    public static StoreItemDetailListRes toDto(List<StoreItemDetail> details) {
        List<StoreItemDetailRes> items = details.stream()
                .map(StoreItemDetailRes::toDto)
                .collect(Collectors.toList());

        return StoreItemDetailListRes.builder()
                .items(items)
                .totalCount(items.size())
                .build();
    }
}
