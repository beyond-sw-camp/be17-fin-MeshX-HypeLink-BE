package MeshX.HypeLink.image.model.dto.request;

import MeshX.HypeLink.image.model.entity.Image;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public final class ImageCreateRequest {

    private final String originalFilename;
    private final Long fileSize;
    private final String contentType;
    private final String s3Key;

    public Image toEntity() {
        return Image.builder()
                .originalFilename(originalFilename)
                .savedPath(s3Key)
                .contentType(contentType)
                .fileSize(fileSize)
                .build();
    }
}

