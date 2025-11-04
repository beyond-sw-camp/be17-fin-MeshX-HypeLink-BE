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
public class AsDetailRes {
    private Integer id;
    private String title;
    private String description;
    private String storeName;
    private Integer storeId;
    private AsStatus status;
    private LocalDateTime createdAt;
    private List<CommentRes> comments;

    public static AsDetailRes from(As as) {
        return AsDetailRes.builder()
                .id(as.getId())
                .title(as.getTitle())
                .description(as.getDescription())
                .storeName(as.getStore().getMember().getName())
                .storeId(as.getStore().getId())
                .status(as.getStatus())
                .createdAt(as.getCreatedAt())
                .comments(as.getComments().stream()
                        .map(CommentRes::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
