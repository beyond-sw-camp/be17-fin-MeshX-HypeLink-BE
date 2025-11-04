package MeshX.HypeLink.head_office.item.model.dto.response;

import MeshX.HypeLink.head_office.item.model.entity.Color;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ColorInfoRes {
    private String colorName;
    private String colorCode;

    public static ColorInfoRes toDto(Color entity) {
        return ColorInfoRes.builder()
                .colorCode(entity.getColorCode())
                .colorName(entity.getColorName())
                .build();
    }
}
