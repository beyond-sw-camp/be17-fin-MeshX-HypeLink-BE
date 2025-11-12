package com.example.apinotice.notice.usecase.port.in.request;

import com.example.apinotice.notice.adaptor.out.jpa.NoticeImageEntity;
import com.example.apinotice.notice.domain.NoticeImage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NoticeImageCreateCommand {
    private String originalFilename;
    private String savedPath;
    private String contentType;
    private Long fileSize;

    public NoticeImage toDomain() {
        return NoticeImage.builder()
                .originalFilename(originalFilename)
                .savedPath(savedPath)
                .contentType(contentType)
                .fileSize(fileSize)
                .build();
    }
}
