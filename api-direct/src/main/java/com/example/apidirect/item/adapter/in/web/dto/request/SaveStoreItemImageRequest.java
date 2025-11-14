package com.example.apidirect.item.adapter.in.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveStoreItemImageRequest {
    private String originalFilename;
    private String savedPath;
    private String contentType;
    private Long fileSize;
    private Integer sortIndex;
}
