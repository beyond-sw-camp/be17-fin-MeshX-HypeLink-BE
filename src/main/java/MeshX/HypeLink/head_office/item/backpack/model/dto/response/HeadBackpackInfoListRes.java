package MeshX.HypeLink.head_office.item.backpack.model.dto.response;


import MeshX.HypeLink.head_office.item.backpack.model.entity.BackPack;
import lombok.Builder;

import java.util.List;

public class HeadBackpackInfoListRes {
    private final List<HeadBackpackInfoRes> backpacks;

    public static HeadBackpackInfoListRes toDto(List<BackPack> entity) {
        return HeadBackpackInfoListRes.builder()
                .backpacks(entity.stream()
                        .map(HeadBackpackInfoRes::toDto)
                        .toList()
                )
                .build();
}

@Builder
private HeadBackpackInfoListRes(List<HeadBackpackInfoRes> backpacks) {
    this.backpacks = backpacks;}
}
