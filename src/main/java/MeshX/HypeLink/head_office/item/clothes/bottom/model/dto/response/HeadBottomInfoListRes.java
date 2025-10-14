package MeshX.HypeLink.head_office.item.clothes.bottom.model.dto.response;


import MeshX.HypeLink.head_office.item.clothes.bottom.model.entity.BottomClothes;
import lombok.Builder;

import java.util.List;

public class HeadBottomInfoListRes {
    private final List<HeadBottomInfoRes> bottoms;

    public static HeadBottomInfoListRes toDto(List<BottomClothes> entity) {
        return HeadBottomInfoListRes.builder()
                .bottoms(entity.stream()
                        .map(HeadBottomInfoRes::toDto)
                        .toList()
                )
                .build();
}

@Builder
private HeadBottomInfoListRes(List<HeadBottomInfoRes> bottoms) {
    this.bottoms = bottoms;}
}
