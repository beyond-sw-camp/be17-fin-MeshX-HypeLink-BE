package MeshX.HypeLink.head_office.notice.model.dto.request;

import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import lombok.Getter;

@Getter
public class NoticeCreateReq {
    private String title;
    private String contents;
    private String author;
    //date?

    public Notice toEntity() {
        return Notice.builder()
                .title(title)
                .contents(contents)
                .author(author)
                .isOpen(true)
                .build();
    }
}
