package MeshX.HypeLink.direct_store.item.shoes.model.dto.response;


import MeshX.HypeLink.direct_store.item.shoes.model.entity.DirectShoes;
import lombok.Builder;

import java.util.List;

public class DirectShoesInfoListRes {
    private final List<DirectShoesInfoRes> shoes;

    public static DirectShoesInfoListRes toDto(List<DirectShoes> entity) {
        return DirectShoesInfoListRes.builder()
                .shoes(entity.stream()
                        .map(DirectShoesInfoRes::toDto)
                        .toList()
                )
                .build();
}

@Builder
private DirectShoesInfoListRes(List<DirectShoesInfoRes> shoes) {
    this.shoes = shoes;}
}
