package com.example.apiitem.item.usecase.port.out.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ImageUploadResponse {
    private Integer id;
    private String originalName;
    private String imageName;
    private String imagePath;
    private String imageUrl;
    private Long imageSize;
}