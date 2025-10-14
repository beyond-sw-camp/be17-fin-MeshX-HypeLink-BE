package MeshX.HypeLink.head_office.item.shoes.model.dto.response;



import MeshX.HypeLink.head_office.item.shoes.model.entity.Shoes;
import lombok.Builder;

import java.util.List;

public class HeadShoesInfoListRes {
    private final List<HeadShoesInfoRes> shoes;

    public static HeadShoesInfoListRes toDto(List<Shoes> entity) {
        return HeadShoesInfoListRes.builder()
                .shoes(entity.stream()
                        .map(HeadShoesInfoRes::toDto)
                        .toList()
                )
                .build();
}

@Builder
private HeadShoesInfoListRes(List<HeadShoesInfoRes> shoes) {
    this.shoes = shoes;}
}
