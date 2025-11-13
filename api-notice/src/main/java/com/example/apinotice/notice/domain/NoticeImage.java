package com.example.apinotice.notice.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NoticeImage {
    private Integer id;
    private String originalFilename;
    private String s3Key;
    private String contentType;
    private Long fileSize;
}
