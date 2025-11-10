package com.example.apinotice.notice.usecase.port.out.response;

import com.example.apinotice.notice.domain.Notice;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NoticeInfoDto {
    private Integer id;
    private String title;
    private String contents;
    private String author;


    public static NoticeInfoDto toDto(Notice notice) {
        return NoticeInfoDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .contents(notice.getContents())
                .author(notice.getAuthor())
                .build();
    }
}
