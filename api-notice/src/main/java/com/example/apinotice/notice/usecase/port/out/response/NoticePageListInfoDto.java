package com.example.apinotice.notice.usecase.port.out.response;

import com.example.apinotice.notice.domain.Notice;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class NoticePageListInfoDto {
    private Integer id;
    private String title;
    private String contents;
    private String author;
    private Boolean isOpen;
    private LocalDateTime date;

    public static NoticePageListInfoDto toDto(Notice notice) {
        LocalDateTime displayDate = notice.getUpdatedAt() != null
                ? notice.getUpdatedAt()
                : notice.getCreatedAt();

        return NoticePageListInfoDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .contents(notice.getContents())
                .author(notice.getAuthor())
                .isOpen(notice.getIsOpen())
                .date(displayDate)
                .build();
    }

    public static Page<NoticePageListInfoDto> toDtoPage(Page<Notice> page) {
        return page.map(NoticePageListInfoDto::toDto);
    }
}
