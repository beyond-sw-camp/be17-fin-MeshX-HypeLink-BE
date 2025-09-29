package MeshX.HypeLink.head_office.notice.model.dto.request;

import lombok.Getter;

@Getter
public class NoticeUpdateReq {
    private String title;
    private String contents;
    private Boolean isOpen;
}
