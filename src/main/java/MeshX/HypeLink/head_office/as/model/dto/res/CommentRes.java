package MeshX.HypeLink.head_office.as.model.dto.res;

import MeshX.HypeLink.auth.model.entity.MemberRole;
import MeshX.HypeLink.head_office.as.model.entity.AsComment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentRes {
    private Integer id;
    private String memberName;
    private MemberRole memberRole;
    private String description;
    private LocalDateTime createdAt;

    public static CommentRes from(AsComment comment) {
        return CommentRes.builder()
                .id(comment.getId())
                .memberName(comment.getMember().getName())
                .memberRole(comment.getMember().getRole())
                .description(comment.getDescription())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
