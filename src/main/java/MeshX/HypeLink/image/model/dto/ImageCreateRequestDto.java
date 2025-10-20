package MeshX.HypeLink.image.model.dto;

import MeshX.HypeLink.image.model.entity.ImageType;

public record ImageCreateRequestDto (String originalFilename, Long fileSize, String contentType,
                                     String s3Key, ImageType imageType) {
}

