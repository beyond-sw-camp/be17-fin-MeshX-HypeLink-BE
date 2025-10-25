package MeshX.HypeLink.head_office.as.model.dto.res;

import MeshX.HypeLink.head_office.as.model.entity.As;
import MeshX.HypeLink.head_office.as.model.entity.AsStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class AsListRes {
    private Integer id;
    private String title;
    private String storeName;
    private AsStatus status;
    private LocalDateTime createdAt;

    public static AsListRes from(As as) {
        return AsListRes.builder()
                .id(as.getId())
                .title(as.getTitle())
                .storeName(as.getStore().getMember().getName())
                .status(as.getStatus())
                .createdAt(as.getCreatedAt())
                .build();
    }

    public static List<AsListRes> fromList(List<As> asList) {
        return asList.stream()
                .map(AsListRes::from)
                .collect(Collectors.toList());
    }
}
