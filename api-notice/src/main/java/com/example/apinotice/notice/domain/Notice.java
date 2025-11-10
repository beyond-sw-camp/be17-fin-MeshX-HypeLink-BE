package com.example.apinotice.notice.domain;

import com.example.apinotice.notice.adaptor.out.jpa.NoticeEntity;
import com.example.apinotice.notice.usecase.port.in.request.NoticeSaveCommand;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Notice {
    private Integer id;
    private String title;
    private String contents;
    private String author;
    private Boolean isOpen;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }

    public void updateAuthor(String author) {
        this.author = author;
    }
    public void changeOpen() {
        this.isOpen = true;
    }

}
