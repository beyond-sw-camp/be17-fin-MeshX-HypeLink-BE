package com.example.apiitem.item.usecase.port.out.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemImageInfoDto {
    private Integer id;
    private Integer index;
    private String originalFilename;
    private String originalName;
    private String imageName;
    private String imagePath;
    private String imageUrl;
    private Long imageSize;
}
