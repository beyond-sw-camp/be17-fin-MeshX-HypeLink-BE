package MeshX.HypeLink.head_office.notice.model.dto.response;

import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
public class NoticeInfoRes {
    private Integer id;
    private String title;
    private String contents;
    private String author;
    private Boolean isOpen;
    private LocalDateTime date;

    public static NoticeInfoRes toDto(Notice entity) {
        LocalDateTime displayDate = entity.getUpdatedAt() != null
                ? entity.getUpdatedAt()
                : entity.getCreatedAt();
        return NoticeInfoRes.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .contents(entity.getContents())
                .author(entity.getAuthor())
                .isOpen(entity.getIsOpen())
                .date(displayDate)
                .build();
    }

    @Builder
    private NoticeInfoRes(String title, String contents, Boolean isOpen, String author, Integer id,  LocalDateTime date) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.isOpen = isOpen;
        this.date = date;
    }
    public static Page<NoticeInfoRes> toDtoPage(Page<Notice> page) { return page.map(NoticeInfoRes::toDto);}
}
