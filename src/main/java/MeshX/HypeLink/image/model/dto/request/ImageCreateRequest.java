package MeshX.HypeLink.image.model.dto.request;

import MeshX.HypeLink.image.model.entity.Image;

public record ImageCreateRequest(String originalFilename, Long fileSize, String contentType,
                                 String s3Key) {

    public Image toEntity() {
        return Image.builder()
                .originalFilename(originalFilename)
                .savedPath(s3Key)
                .contentType(contentType)
                .fileSize(fileSize)
                .build();
    }
}

