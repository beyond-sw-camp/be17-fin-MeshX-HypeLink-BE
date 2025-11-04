package MeshX.HypeLink.head_office.item.model.dto.response;

import MeshX.HypeLink.head_office.item.model.entity.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SizeInfoRes {
    private String size;

    public static SizeInfoRes toDto(Size entity) {
        return SizeInfoRes.builder()
                .size(entity.getSize())
                .build();
    }
}
