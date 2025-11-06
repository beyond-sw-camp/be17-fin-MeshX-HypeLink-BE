package com.example.apiitem.item.domain;

import MeshX.common.Domain;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Domain
public class ItemImage {
    private Integer id;
    private Integer itemId;
    private Integer sortIndex;
    private String originalFilename;
    private String savedPath;
    private String contentType;
    private Long fileSize;
}
