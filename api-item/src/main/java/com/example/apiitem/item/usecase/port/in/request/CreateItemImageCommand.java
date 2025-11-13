package com.example.apiitem.item.usecase.port.in.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class CreateItemImageCommand {
    private Integer id;  // ← 이미지 ID 추가
    private String originalFilename;
    private Long fileSize;
    private String contentType;
    private String s3Key;
    private Integer index;
}
