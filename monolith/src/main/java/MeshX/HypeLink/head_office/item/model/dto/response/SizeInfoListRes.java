package MeshX.HypeLink.head_office.item.model.dto.response;

import MeshX.HypeLink.head_office.item.model.entity.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SizeInfoListRes {
    private List<SizeInfoRes> sizes;

    public static SizeInfoListRes toDto(List<Size> entities) {
        return SizeInfoListRes.builder()
                .sizes(entities.stream().map(SizeInfoRes::toDto).toList())
                .build();
    }
}
