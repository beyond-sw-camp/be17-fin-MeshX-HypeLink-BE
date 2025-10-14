package MeshX.HypeLink.direct_strore.item.backpack.model.dto.response;

import MeshX.HypeLink.direct_strore.item.backpack.model.entity.DirectBackPack;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

public class DirectBackpackInfoListRes {
    private final List<DirectBackpackInfoRes> backpacks;

    public static DirectBackpackInfoListRes toDto(List<DirectBackPack> entity) { //비페이징용
        return DirectBackpackInfoListRes.builder()
                .backpacks(entity.stream()
                        .map(DirectBackpackInfoRes::toDto)
                        .toList()
                )
                .build();
    }


    @Builder
    private DirectBackpackInfoListRes(List < DirectBackpackInfoRes > backpacks) {
                this.backpacks = backpacks;
            }
}
