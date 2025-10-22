package MeshX.HypeLink.head_office.notice.model.dto.response;

import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
public class NoticeListResponse {
    private Integer id;
    private String title;
    private String contents;
    private String author;
    private Boolean isOpen;
    private LocalDateTime date;

    public static NoticeListResponse toDto(Notice entity) {
        LocalDateTime displayDate = entity.getUpdatedAt() != null
                ? entity.getUpdatedAt()
                : entity.getCreatedAt();

        return NoticeListResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .contents(entity.getContents())
                .isOpen(entity.getIsOpen())
                .author(entity.getAuthor())
                .date(displayDate)
                .build();
    }

    @Builder
    private NoticeListResponse(String title, String contents, Boolean isOpen, String author, Integer id, LocalDateTime date) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.isOpen = isOpen;
        this.date = date;
    }

    public static Page<NoticeListResponse> toDtoPage(Page<Notice> page) {
        return page.map(NoticeListResponse::toDto);
    }
}
