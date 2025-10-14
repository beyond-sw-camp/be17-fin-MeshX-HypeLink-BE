package MeshX.HypeLink.head_office.item.clothes.top.model.dto.response;




import MeshX.HypeLink.head_office.item.clothes.top.model.entity.TopClothes;
import lombok.Builder;

import java.util.List;

public class HeadTopInfoListRes {
    private final List<HeadTopInfoRes> tops;

    public static HeadTopInfoListRes toDto(List<TopClothes> entity) {
        return HeadTopInfoListRes.builder()
                .tops(entity.stream()
                        .map(HeadTopInfoRes::toDto)
                        .toList()
                )
                .build();
}

@Builder
private HeadTopInfoListRes(List<HeadTopInfoRes> tops) {
    this.tops = tops;}
}
