package com.example.apinotice.notice.usecase.port.in.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NoticeUpdateCommand {
    private String title;
    private String contents;
    private String author;
    private Boolean isOpen;
}
