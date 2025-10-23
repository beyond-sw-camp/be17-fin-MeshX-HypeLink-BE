package MeshX.HypeLink.head_office.item.model.dto.response;

import MeshX.HypeLink.head_office.item.model.entity.Color;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ColorInfoListRes {
    private List<ColorInfoRes> colors;

    public static ColorInfoListRes toDto(List<Color> entities) {
        return ColorInfoListRes.builder()
                .colors(entities.stream().map(ColorInfoRes::toDto).toList())
                .build();
    }
}
