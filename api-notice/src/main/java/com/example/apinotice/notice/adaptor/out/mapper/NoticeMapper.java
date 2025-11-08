package com.example.apinotice.notice.adaptor.out.mapper;

import com.example.apinotice.notice.adaptor.out.jpa.NoticeEntity;
import com.example.apinotice.notice.domain.Notice;
import com.example.apinotice.notice.usecase.port.in.request.NoticeSaveCommand;

public class NoticeMapper {
    private NoticeMapper(){}

    public static NoticeEntity toEntity(Notice notice){
       return NoticeEntity.builder()
               .id(notice.getId())
               .title(notice.getTitle())
               .contents(notice.getContents())
               .author(notice.getAuthor())
               .build();
    }

    public static Notice toDomain(NoticeEntity noticeEntity){
        return Notice.builder()
                .id(noticeEntity.getId())
                .title(noticeEntity.getTitle())
                .contents((noticeEntity.getContents()))
                .author(noticeEntity.getAuthor())
                .build();
    }

    public static Notice toDomain(NoticeSaveCommand command){
        return Notice.builder()
                .title(command.getTitle())
                .contents((command.getContents()))
                .author(command.getAuthor())
                .build();
    }
}
