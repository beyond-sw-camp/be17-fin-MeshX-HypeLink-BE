package MeshX.HypeLink.direct_store.as.model.dto;

import MeshX.HypeLink.head_office.as.model.dto.res.CommentRes;
import MeshX.HypeLink.head_office.as.model.entity.As;
import MeshX.HypeLink.head_office.as.model.entity.AsStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class MyAsDetailRes {
    private Integer id;
    private String title;
    private String description;
    private AsStatus status;
    private LocalDateTime createdAt;
    private List<CommentRes> comments;

    public static MyAsDetailRes fromEntity(As as) {
        return MyAsDetailRes.builder()
                .id(as.getId())
                .title(as.getTitle())
                .description(as.getDescription())
                .status(as.getStatus())
                .createdAt(as.getCreatedAt())
                .comments(as.getComments().stream()
                        .map(CommentRes::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
