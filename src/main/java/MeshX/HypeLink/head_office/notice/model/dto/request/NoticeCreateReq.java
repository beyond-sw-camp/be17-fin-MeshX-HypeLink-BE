package MeshX.HypeLink.head_office.notice.model.dto.request;

import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import MeshX.HypeLink.image.model.dto.request.ImageCreateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NoticeCreateReq {
    private String title;
    private String contents;
    private List<ImageCreateRequest> images;

    public Notice toEntity() {
        return Notice.builder()
                .title(title)
                .contents(contents)
                .isOpen(true)
                .build();
    }
}
