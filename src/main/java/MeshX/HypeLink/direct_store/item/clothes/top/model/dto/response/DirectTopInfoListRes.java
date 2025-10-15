package MeshX.HypeLink.direct_store.item.clothes.top.model.dto.response;



import MeshX.HypeLink.direct_store.item.clothes.top.model.entity.DirectTopClothes;
import lombok.Builder;

import java.util.List;

public class DirectTopInfoListRes {
    private final List<DirectTopInfoRes> tops;

    public static DirectTopInfoListRes toDto(List<DirectTopClothes> entity) {
        return DirectTopInfoListRes.builder()
                .tops(entity.stream()
                        .map(DirectTopInfoRes::toDto)
                        .toList()
                )
                .build();
}

@Builder
private DirectTopInfoListRes(List<DirectTopInfoRes> tops) {
    this.tops = tops;}
}
