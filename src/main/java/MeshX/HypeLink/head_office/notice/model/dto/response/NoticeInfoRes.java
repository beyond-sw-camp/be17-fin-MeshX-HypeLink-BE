package MeshX.HypeLink.head_office.notice.model.dto.response;

import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticeInfoRes {
    private String title;
    private String contents;
    private Boolean isOpen;

    public static NoticeInfoRes toDto(Notice entity) {
        return NoticeInfoRes.builder()
                .title(entity.getTitle())
                .contents(entity.getContents())
                .isOpen(entity.getIsOpen())
                .build();
    }

    @Builder
    private NoticeInfoRes(String title, String contents, Boolean isOpen) {
        this.title = title;
        this.contents = contents;
        this.isOpen = isOpen;
    }
}
