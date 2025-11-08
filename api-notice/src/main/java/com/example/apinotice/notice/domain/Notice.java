package com.example.apinotice.notice.domain;

import com.example.apinotice.notice.adaptor.out.jpa.NoticeEntity;
import com.example.apinotice.notice.usecase.port.in.request.NoticeSaveCommand;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Notice {
    private Integer id;
    private String title;
    private String contents;
    private String author;


//    public static Notice from(NoticeSaveCommand noticeSaveCommand) {
//        return Notice.builder()
//                .title(noticeSaveCommand.getTitle())
//                .contents(noticeSaveCommand.getContents())
//                .author(noticeSaveCommand.getAuthor())
//                .build();
//
//    }

//    public  NoticeEntity toEntity() {
//        return NoticeEntity.builder()
//                .title(this.title)
//                .contents(this.contents)
//                .author(this.author)
//                .build();
//    }
}
