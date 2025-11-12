package org.example.apidirect.item.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreItemImage {
    private Integer id;
    private Integer sortIndex;
    private String originalFilename;
    private String savedPath;
    private String contentType;
    private Long fileSize;
}
