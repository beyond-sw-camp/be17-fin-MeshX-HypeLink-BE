package MeshX.HypeLink.head_office.as.model.dto.req;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.head_office.as.model.entity.As;
import MeshX.HypeLink.head_office.as.model.entity.AsComment;
import lombok.Getter;

@Getter
public class CommentCreateReq {
    private String description;

    public AsComment toEntity(As as, Member member) {
        return AsComment.builder()
                .as(as)
                .member(member)
                .description(description)
                .build();
    }
}
