package MeshX.HypeLink.head_office.notice.model.dto.response;

import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class NoticeInfoRes {
    private String title;
    private String contents;
    private String author;
    private Boolean isOpen;

    public static NoticeInfoRes toDto(Notice entity) {
        return NoticeInfoRes.builder()
                .title(entity.getTitle())
                .contents(entity.getContents())
                .author(entity.getAuthor())
                .isOpen(entity.getIsOpen())
                .build();
    }

    @Builder
    private NoticeInfoRes(String title, String contents, Boolean isOpen, String author) {
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.isOpen = isOpen;
    }
    public static Page<NoticeInfoRes> toDtoPage(Page<Notice> page) { return page.map(NoticeInfoRes::toDto);}
}
