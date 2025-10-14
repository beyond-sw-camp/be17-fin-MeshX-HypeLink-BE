package MeshX.HypeLink.head_office.item.clothes.outer.model.dto.response;



import MeshX.HypeLink.head_office.item.clothes.outer.model.entity.OuterClothes;
import lombok.Builder;

import java.util.List;

public class HeadOuterInfoListRes {
    private final List<HeadOuterInfoRes> outers;

    public static HeadOuterInfoListRes toDto(List<OuterClothes> entity) {
        return HeadOuterInfoListRes.builder()
                .outers(entity.stream()
                        .map(HeadOuterInfoRes::toDto)
                        .toList()
                )
                .build();
}

@Builder
private HeadOuterInfoListRes(List<HeadOuterInfoRes> outers) {
    this.outers = outers;}
}
