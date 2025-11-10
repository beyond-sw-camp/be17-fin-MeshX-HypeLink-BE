package com.example.apinotice.notice.usecase.port.out.response;

import com.example.apinotice.notice.domain.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NoticeInfoDto {
    private Integer id;
    private String title;
    private String contents;
    private String author;
    private Boolean isOpen;
    private LocalDateTime date;


    public static NoticeInfoDto toDto(Notice notice) {
        LocalDateTime displayDate = (notice.getUpdatedAt() != null) ? notice.getUpdatedAt() : notice.getCreatedAt();
        return NoticeInfoDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .contents(notice.getContents())
                .author(notice.getAuthor())
                .isOpen(notice.getIsOpen())
                .date(displayDate)
                .build();
    }
}
