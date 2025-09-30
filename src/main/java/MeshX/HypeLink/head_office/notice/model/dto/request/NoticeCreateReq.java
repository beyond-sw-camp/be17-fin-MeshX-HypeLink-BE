package MeshX.HypeLink.head_office.notice.model.dto.request;

import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import lombok.Getter;

@Getter
public class NoticeCreateReq {
    private String title;
    private String contents;

    public Notice toEntity() {
        return Notice.builder()
                .title(title)
                .contents(contents)
                .isOpen(true)
                .build();
    }
}
