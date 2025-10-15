package MeshX.HypeLink.direct_store.item.clothes.bottom.model.dto.response;

import MeshX.HypeLink.direct_store.item.clothes.bottom.model.entity.DirectBottomClothes;
import lombok.Builder;

import java.util.List;

public class DirectBottomInfoListRes {
    private final List<DirectBottomInfoRes> bottoms;

    public static DirectBottomInfoListRes toDto(List<DirectBottomClothes> entity) {
        return DirectBottomInfoListRes.builder()
                .bottoms(entity.stream()
                        .map(DirectBottomInfoRes::toDto)
                        .toList()
                )
                .build();
}

@Builder
private DirectBottomInfoListRes(List<DirectBottomInfoRes> bottoms) {
    this.bottoms = bottoms;}
}
