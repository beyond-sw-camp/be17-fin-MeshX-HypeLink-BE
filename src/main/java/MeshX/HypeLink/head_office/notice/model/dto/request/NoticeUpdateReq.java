package MeshX.HypeLink.head_office.notice.model.dto.request;

import MeshX.HypeLink.image.model.dto.request.ImageCreateRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class NoticeUpdateReq {
    private Integer id;
    private String title;
    private String contents;
    private Boolean isOpen;
    private List<ImageCreateRequest> images;
    private String author;
}
