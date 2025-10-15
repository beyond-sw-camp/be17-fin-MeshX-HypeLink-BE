package MeshX.HypeLink.direct_store.item.clothes.outer.model.dto.response;


import MeshX.HypeLink.direct_store.item.clothes.outer.model.entity.DirectOuterClothes;
import lombok.Builder;

import java.util.List;

public class DirectOuterInfoListRes {
    private final List<DirectOuterInfoRes> outers;

    public static DirectOuterInfoListRes toDto(List<DirectOuterClothes> entity) {
        return DirectOuterInfoListRes.builder()
                .outers(entity.stream()
                        .map(DirectOuterInfoRes::toDto)
                        .toList()
                )
                .build();
}

@Builder
private DirectOuterInfoListRes(List<DirectOuterInfoRes> outers) {
    this.outers = outers;}
}
