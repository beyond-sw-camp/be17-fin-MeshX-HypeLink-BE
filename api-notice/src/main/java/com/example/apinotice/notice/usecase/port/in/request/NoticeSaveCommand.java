package com.example.apinotice.notice.usecase.port.in.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NoticeSaveCommand {
    private String title;
    private String contents;
    private List<NoticeImageCreateCommand> images;
    private String author;
}
