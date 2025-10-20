package MeshX.HypeLink.head_office.notice.model.dto.request;

import MeshX.HypeLink.image.model.dto.request.ImageCreateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NoticeUpdateReq {
    private String title;
    private String contents;
    private Boolean isOpen;
    private List<ImageCreateRequest> images;
}
